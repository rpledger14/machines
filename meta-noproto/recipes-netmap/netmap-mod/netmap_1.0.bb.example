DESCRIPTION = "Netmap Package"
SUMMARY = "Netmap Linux kernel module"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../COPYING;md5=f47802811bb25231d524e6602f85c285"
inherit module

PV = "1.0"
PR = "r0"

# ***Change this to pull from your github instead
SRC_URI = "file://netmap11_0_3.tar.gz \
"
#Build Directory
S = "${WORKDIR}/LINUX"

# The inherit of module.bbclass will automatically name module packages with
# kernel-module-" prefix as required by the oe-core build environment.

do_compile(){

./configure --install-mod-path=${D} --cc=powerpc-fsl-linux-gnuspe-gcc --kernel-dir=${STAGING_KERNEL_DIR}  --kernel-sources=/opt/QorIQ-SDK-V1.6-20140811-yocto/build_p1020wlan_release/tmp/work/p1020wlan-fsl-linux-gnuspe/linux-qoriq-sdk/3.12-r0/git --no-drivers
make clean
make
make apps

}
do_install() {
make install
make DESTDIR=${D} install-apps
}

# To put all of the example apps in a package
FILES_${PN} = "usr/*"

# Gets rid of .debug error in do_package
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
