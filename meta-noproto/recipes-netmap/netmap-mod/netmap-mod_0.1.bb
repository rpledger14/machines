# Bitbake file for Netmap kernel module
SUMMARY = "Netmap Linux kernel module"
LICENSE = "Apache"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

# Version of Recipe
PR = "r0"
PV = "0.1"

# Flowgrammable forked Netmap Repo
SRC_URI = "git://github.com/flowgrammable/netmap.git;branch=next;protocol=https \
           file://ipv6_checksum.patch \
           file://LICENSE \
           "
SRCREV = "6feb4b449b118b60dbf4e376730f54380553d2dd"

# Directory Variables
S = "${WORKDIR}"
#K = "${STAGING_KERNEL_DIR}"

# Hard-coded kernel source directory -- not obvious how to acquire this within the bitbake environment.  Yes, that is ridiculous... 
K = "/opt/QorIQ-SDK-V1.6-20140811-yocto/build_p1020wlan_noproto/tmp/work/p1020wlan-fsl-linux-gnuspe/linux-qoriq-sdk/3.12-r0/git"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
inherit module

# Automatic configure doesn't work because of LINUX sub-directory for Netmap
#EXTRA_OECONF = "--kernel-dir=${K} --kernel-sources=${K}"

# Override the configure and make steps
do_configure() {
  #printenv
  echo "kernel sources: ${K}"
  echo "working directory: ${S}"
  cd ${S}/git/LINUX
  ./configure --kernel-dir=${K} --kernel-sources=${K} --disable-vale
  #oe_runconf
}

do_compile() {
  # Uses ld instead of gcc to link.  Hack to remove linker flags.  Fix later!
  unset LDFLAGS
  cd ${S}/git/LINUX
  #make -n
  #make -d
  make
  #oe_runmake
}

do_install() {
  echo ${D}
  #module_do_
}

