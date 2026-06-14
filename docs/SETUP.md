# Build Setup Instructions

## 1. Clone Poky and meta-raspberrypi
```bash
git clone -b scarthgap https://git.yoctoproject.org/poky.git
git clone -b scarthgap https://git.yoctoproject.org/meta-raspberrypi.git
```

## 2. Initialize build environment
```bash
source poky/oe-init-build-env builds/rpi5
```

## 3. Add layers
```bash
bitbake-layers add-layer ../meta-raspberrypi
bitbake-layers add-layer ../meta-custom
```

## 4. Apply local.conf additions
Append the contents of `meta-custom/docs/local.conf.append` to `builds/rpi5/conf/local.conf`.

Generate your own password hashes:
```bash
openssl passwd -6 yourpassword
```

## 5. Build
```bash
bitbake core-image-minimal
```
