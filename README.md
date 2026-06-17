# Yocto RPi5 Tutorial

A hands-on, step-by-step Yocto Project tutorial for building a custom Linux
image for Raspberry Pi 5, using Poky Scarthgap (5.0 LTS).

This repo contains a custom layer (`meta-custom`) plus documentation covering
the full process from a fresh host machine to a booted, SSH-accessible
RPi 5 running a custom image.

## What You'll Build

- A minimal Linux image for RPi 5
- SSH access with custom user accounts
- Sudo configured for a non-root user
- Your own Yocto layer with custom recipes

## Prerequisites

- A Linux host machine (8GB+ RAM, 100GB+ free disk space recommended)
- Docker (works around host OS incompatibilities, e.g. non-Debian distros)
- A Raspberry Pi 5
- A microSD card (8GB+) and a way to flash it

## Project Structure

```
yocto-rpi5-fundamentals/
├── docs/
│   ├── 01-environment-setup.md
│   ├── 02-first-build.md
│   ├── 03-user-setup.md
│   ├── 04-custom-recipe-systemd-service.md
│   └── local.conf.append
└── meta-custom/
    ├── conf/
    │   └── layer.conf
    ├── recipes-example/
    │   └── sysinfo/
    │       ├── files/
    │       │   ├── sysinfo.c
    │       │   └── sysinfo.service
    │       └── sysinfo_0.1.bb
    └── recipes-extended/
        └── sudo/
            └── sudo_%.bbappend
```

## Quick Start

See `docs/` for detailed step-by-step instructions, starting with
`01-environment-setup.md`.

## Layers Used

| Layer | Purpose |
|---|---|
| `poky` (Scarthgap) | Core Yocto build system |
| `meta-raspberrypi` | RPi 5 hardware support |
| `meta-custom` | This repo — custom recipes and configuration |

## Status

- [x] Docker build environment on non-Debian host
- [x] First image build — `core-image-minimal` for RPi 5
- [x] SSH access
- [x] Custom users and sudo privileges
- [x] Custom layer — `meta-custom`
- [x] Custom recipe — C program cross-compiled for ARM64
- [x] systemd service running at boot
- [ ] Cross-compile a more complex application
- [ ] Custom kernel configuration