SUMMARY = "Simple demo sysinfo tool that reads /proc/cpuinfo"
DESCRIPTION = "Custom example for the Yocto tutorial"
LICENSE = "CLOSED"

SRC_URI = "file://sysinfo.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} sysinfo.c -o sysinfo
}

do_install(){
    install -d ${D}${bindir}
    install -m 0755 sysinfo ${D}${bindir}/sysinfo
}
