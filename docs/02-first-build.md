# 02 — First Build

## 1. Clone Poky and meta-raspberrypi

Inside the Docker container:

```bash
cd /workspace/sources

git clone -b scarthgap https://git.yoctoproject.org/poky.git
git clone -b scarthgap https://git.yoctoproject.org/meta-raspberrypi.git
```

> **Note:** Use HTTPS, not git:// — port 9418 is often blocked by firewalls.

## 2. Initialize Build Environment

```bash
source /workspace/sources/poky/oe-init-build-env /workspace/builds/rpi5
```

This script sets up BitBake environment variables and creates the build
directory with default `conf/local.conf` and `conf/bblayers.conf`.
You land automatically in `/workspace/builds/rpi5`.

> **Note:** Always run this command when starting a new container session
> before running any bitbake commands.

## 3. Add Layers

```bash
bitbake-layers add-layer /workspace/sources/meta-raspberrypi
bitbake-layers add-layer /workspace/sources/meta-custom
```

Verify:

```bash
bitbake-layers show-layers
```

Expected output:

```bash
layer                 path                                      priority
core                  /workspace/sources/poky/meta              5
yocto                 /workspace/sources/poky/meta-poky         5
yoctobsp              /workspace/sources/poky/meta-yocto-bsp    5
raspberrypi           /workspace/sources/meta-raspberrypi       9
meta-custom           /workspace/sources/meta-custom            6
```

## 4. Configure local.conf

Edit `conf/local.conf`:

```bash
nano conf/local.conf
```

Apply all changes from `docs/local.conf.append` — see that file for
the full list. Key changes:

```bitbake
MACHINE ??= "raspberrypi5"
DL_DIR ?= "/workspace/downloads"
SSTATE_DIR ?= "/workspace/sstate-cache"
INIT_MANAGER = "systemd"
```

> **Note:** `DL_DIR` and `SSTATE_DIR` point outside the build directory
> so downloads and cached build artifacts survive across builds and
> container restarts.

## 5. Build

```bash
bitbake core-image-minimal
```

First build takes 1-3 hours depending on hardware. Subsequent builds
are much faster due to sstate cache.

## 6. Flash to SD Card

From the host machine:

```bash
bzcat /mnt/yocto/builds/rpi5/tmp/deploy/images/raspberrypi5/core-image-minimal-raspberrypi5.rootfs.wic.bz2 \
  | sudo dd of=/dev/sdX bs=4M status=progress conv=fsync
sync
```

Replace `/dev/sdX` with your SD card device. Verify with `lsblk` first.

## 7. Boot and Verify

Insert SD card into RPi 5, connect ethernet, power on.

Login as `root` (no password by default with `core-image-minimal`).

Verify SSH:

```bash
ssh root@<board-ip>
```
