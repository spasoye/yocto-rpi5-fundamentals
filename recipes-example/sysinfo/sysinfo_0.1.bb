SUMMARY = "Simple demo sysinfo tool that reads /proc/cpuinfo"
DESCRIPTION = "Custom example for the Yocto tutorial"
LICENSE = "CLOSED"

SRC_URI = "file://sysinfo.c \
           file://sysinfo.service"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "sysinfo.service"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} sysinfo.c -o sysinfo
}

do_install(){
    install -d ${D}${bindir}
    install -m 0755 sysinfo ${D}${bindir}/sysinfo

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/sysinfo.service ${D}${systemd_system_unitdir}/sysinfo.service
}

FILES:${PN} += "${systemd_system_unitdir}/sysinfo.service"

