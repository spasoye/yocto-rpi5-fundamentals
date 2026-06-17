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
meta-custom/

├── conf/

│   └── layer.conf

├── docs/

│   ├── 01-environment-setup.md

│   ├── 02-first-build.md

│   ├── 03-custom-layer.md

│   └── local.conf.append

├── recipes-extended/

│   └── sudo/

│       └── sudo_%.bbappend

└── recipes-example/

└── example/

└── example_0.1.bb

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

🚧 Work in progress — documenting as I learn.
