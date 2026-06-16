# Step 2: Custom Recipe with systemd Service

## Goal

Write a custom C program, create a Yocto recipe to cross-compile it for
ARM64, add it to the image, and run it as a systemd service.

## 1. Switch to systemd Init

Add to `local.conf`:
INIT_MANAGER = "systemd"

This changes `DISTRO_FEATURES`, which causes many packages to be
reconfigured/rebuilt with systemd support (expect a longer build the
first time after this change).

## 2. Write the C Program

`recipes-example/sysinfo/files/sysinfo.c` — reads `/proc/cpuinfo` and
prints CPU architecture details (ARM-specific fields: CPU architecture,
CPU variant, CPU part, CPU revision, BogoMIPS — note ARM `/proc/cpuinfo`
differs significantly from x86).

## 3. Write the Recipe

`recipes-example/sysinfo/sysinfo_0.1.bb`:

```bitbake
SUMMARY = "Simple demo sysinfo tool that reads /proc/cpuinfo"
DESCRIPTION = "Custom example for the Yocto tutorial"
LICENSE = "CLOSED"

SRC_URI = "file://sysinfo.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} sysinfo.c -o sysinfo
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 sysinfo ${D}${bindir}/sysinfo
}
```

Key points:
- `LICENSE = "CLOSED"` skips `LIC_FILES_CHKSUM` for own throwaway code
- `S = "${WORKDIR}"` — source is a single loose file, not an extracted tarball
- `${CC}`, `${CFLAGS}`, `${LDFLAGS}` ensure correct cross-compilation flags

## 4. Add to Image

In `local.conf`:
IMAGE_INSTALL:append = " sudo iproute2 vim sysinfo"

## 5. systemd Service (next)

`recipes-example/sysinfo/files/sysinfo.service` — runs `sysinfo` as a
oneshot systemd service at boot, output captured in the journal.

## Build and Test

```bash
bitbake sysinfo
bitbake core-image-minimal
```

Flash, boot, and verify:
```bash
sysinfo
systemctl status sysinfo
journalctl -u sysinfo
```
