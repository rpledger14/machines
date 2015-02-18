# Bitbake file for Netmap example applications
SUMMARY = "Netmap Linux example applications"
LICENSE = "Apache"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

# Version of Recipe
PR = "r0"
PV = "0.2"

# Flowgrammable forked Netmap Repo
SRC_URI = "git://github.com/flowgrammable/netmap.git;branch=next;protocol=https \
           file://ipv6_checksum.patch \
           file://LICENSE \
           "
SRCREV = "6feb4b449b118b60dbf4e376730f54380553d2dd"

# Dependancies
DEPENDS += "virtual/kernel"
export OS = "${TARGET_OS}"
export CROSS_COMPILE = "${TARGET_PREFIX}"
export KERNEL_VERSION = "${@base_read_file('${STAGING_KERNEL_DIR}/kernel-abiversion')}"
export PACKAGE_ARCH = "${MACHINE_ARCH}"
export ARCH = "powerpc"

# Directory Variables
S = "${WORKDIR}"
#K = "${STAGING_KERNEL_DIR}"

# Hard-coded kernel source directory -- not obvious how to acquire this within the bitbake environment.  Yes, that is ridiculous... 
K = "/opt/QorIQ-SDK-V1.6-20140811-yocto/build_p1020wlan_noproto/tmp/work/p1020wlan-fsl-linux-gnuspe/linux-qoriq-sdk/3.12-r0/git"

# Makefile-Based Project
CC = "powerpc-fsl-linux-gnuspe-gcc"
LD = "powerpc-fsl-linux-gnuspe-ld"

# Override the configure and make steps
do_configure() {
  echo "kernel sources: ${K}"
  echo "working directory: ${S}"
  cd ${S}/git/LINUX
  ./configure --destdir=${D} --cc=${CC} --kernel-dir=${STAGING_KERNEL_DIR} --kernel-sources=${K} --disable-vale
}

do_compile() {
  # Uses ld instead of gcc to link.  Hack to remove linker flags.  Fix later!
  unset LDFLAGS
  cd ${S}/git/LINUX
  make apps
}

do_install() {
  cd ${S}/git/LINUX
  echo ${D}
  make install-apps
}

# To put all of the example apps in a package
FILES_${PN} = "usr/*"

# Gets rid of .debug error in do_package
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

