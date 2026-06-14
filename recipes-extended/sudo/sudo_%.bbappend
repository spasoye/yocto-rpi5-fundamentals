do_install:append() {
    install -d ${D}${sysconfdir}/sudoers.d
    echo "rpi ALL=(ALL) ALL" > ${D}${sysconfdir}/sudoers.d/rpi
    chmod 0440 ${D}${sysconfdir}/sudoers.d/rpi
}

FILES:${PN} += "${sysconfdir}/sudoers.d/rpi"   
