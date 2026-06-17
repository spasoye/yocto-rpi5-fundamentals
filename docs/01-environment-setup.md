# 01 — Environment Setup

## Host Requirements

Yocto officially supports Ubuntu/Debian. If you're on a different
distribution (e.g. Arch-based like Manjaro), use Docker to get a
compatible build environment.

| Resource | Minimum | Recommended |
|---|---|---|
| RAM | 8 GB | 16 GB+ |
| Disk | 50 GB | 100 GB+ |
| CPU cores | 4 | 8+ |

## 1. Prepare Dedicated Storage

Yocto builds consume 50-100GB+ of disk space. Dedicate a drive or
partition. Make it accessible to your user:

```bash
sudo mkdir -p /mnt/yocto
sudo chown -R $(whoami):$(whoami) /mnt/yocto
chmod 777 /mnt/yocto
mkdir -p /mnt/yocto/{sources,builds,downloads,sstate-cache}
```

## 2. Docker Build Environment

Create a Dockerfile based on Ubuntu 22.04:

```dockerfile
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV LANG=en_US.UTF-8

RUN apt-get update && apt-get install -y \
    gawk wget git diffstat unzip texinfo gcc build-essential \
    chrpath socat cpio python3 python3-pip python3-pexpect \
    xz-utils debianutils iputils-ping python3-git python3-jinja2 \
    python3-subunit zstd liblz4-tool file locales libacl1 lz4 \
    && locale-gen en_US.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

RUN useradd -ms /bin/bash yocto
USER yocto
WORKDIR /workspace
```

Build the image:

```bash
docker build -t yocto-builder .
```

## 3. Start the Container

```bash
docker run -it \
  --name yocto-rpi5 \
  -v /mnt/yocto:/workspace \
  yocto-builder
```

If you dont kill and remove the container you can start it again with:

```bash
docker start -ai yocto-rpi5
docker exec -it yocto-rpi5 bash
```

This way you can keep your bash history and progress in the build environment.

**Critical:** Verify the volume mount works before doing anything else:

```bash
touch /workspace/test
```

Check from the host that the file appears at `/mnt/yocto/test`.
If it doesn't appear, fix permissions before proceeding — build
output may end up trapped inside the container's writable layer
and lost on container restart.

```bash
# Fix from host if needed
chmod 777 /mnt/yocto
```
