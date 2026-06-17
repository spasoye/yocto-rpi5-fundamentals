# 03 — User Setup (rpi with sudo)

## Goal

Add a non-root user `rpi` with sudo privileges baked into the image.
This avoids logging in as root and is required for any real workflow.

## 1. Generate Password Hashes

On the host (or inside the container), generate SHA-512 hashes for both passwords:

```bash
openssl passwd -6 yourpassword
```

Run it twice — once for `root`, once for `rpi`. Copy the output hashes;
you will paste them into `local.conf` in the next step.

> **Note:** The hash string contains `$` characters. In `local.conf` these
> must **not** be escaped — paste them as-is inside single quotes.

## 2. Configure extrausers in local.conf

Add to `conf/local.conf`:

```bitbake
INHERIT += "extrausers"
EXTRA_USERS_PARAMS = "\
    usermod -p '<ROOT_PASSWORD_HASH>' root; \
    useradd -m rpi; \
    usermod -p '<RPI_PASSWORD_HASH>' rpi; \
    usermod -aG sudo rpi; \
"
```

Replace `<ROOT_PASSWORD_HASH>` and `<RPI_PASSWORD_HASH>` with the hashes
from step 1. Keep the single quotes around each hash.

`extrausers` is a Yocto class that runs user management commands directly
against the target rootfs during the image build — no recipe needed.

## 3. Add sudo to the Image

In `local.conf`, append `sudo` to `IMAGE_INSTALL`:

```bitbake
IMAGE_INSTALL:append = " sudo iproute2 vim"
```

Installing `sudo` alone is not enough — the `sudo` package ships without
any sudoers rules by default.

## 4. Configure sudoers via .bbappend

Create `meta-custom/recipes-extended/sudo/sudo_%.bbappend`:

```bitbake
do_install:append() {
    install -d ${D}${sysconfdir}/sudoers.d
    echo "rpi ALL=(ALL) ALL" > ${D}${sysconfdir}/sudoers.d/rpi
    chmod 0440 ${D}${sysconfdir}/sudoers.d/rpi
}

FILES:${PN} += "${sysconfdir}/sudoers.d/rpi"
```

This appends to the upstream `sudo` recipe's install step, dropping a
drop-in sudoers file that grants `rpi` full sudo access.

`%` in `sudo_%.bbappend` is a wildcard that matches any version of the
`sudo` recipe, so this stays valid across Yocto updates.

## 5. Build and Flash

```bash
bitbake core-image-minimal
```

Flash the new image (same `bzcat | dd` command as in step 02).

## 6. Verify

```bash
ssh rpi@<board-ip>
sudo ls /root  # should succeed after password prompt
```
