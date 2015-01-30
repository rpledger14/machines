# Bitbake file for Netmap kernel module
SUMMARY = "Netmap Linux kernel module"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module

# Version of Recipe
PR = "r0"
PV = "0.1"

# Flowgrammable forked Netmap Repo
SRCREV = "6c652c2b0438d8178dda2a9dd720908844776357"
SRC_URI = "git://github.com/flowgrammable/netmap.git;protocol=https \
           file://COPYING \
           "

# Original Netmap Repo
#SRCREV = "9f9028a779140e86cc4a754223d45d1085ac1595"
#SRC_URI = "git://code.google.com/p/netmap/;protocol=http \
#           file://COPYING \
#           file://netmap.mask.in.patch \
#           "

# Tar Archive of Netmap Repo
#SRC_URI = "https://netmap.googlecode.com/archive/9f9028a779140e86cc4a754223d45d1085ac1595.tar.gz"
#SRC_URI[md5sum] = "6397d11f65bb713bd02c78ef736fee82"


# Directory Variables
S = "${WORKDIR}"
#K = "${STAGING_KERNEL_DIR}"

# Hard-coded kernel source directory -- not obvious how to acquire this within the bitbake environment.  Yes, that is ridiculous... 
K = "/opt/QorIQ-SDK-V1.6-20140811-yocto/build_p1020wlan_noproto/tmp/work/p1020wlan-fsl-linux-gnuspe/linux-qoriq-sdk/3.12-r0/git"


# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

# Automatic configure doesn't work because of LINUX sub-directory for Netmap
#EXTRA_OECONF = "--kernel-dir=${K} --kernel-sources=${K}"

# Override the configure and make steps
do_configure() {
  #printenv
  echo "kernel sources: ${K}"
  echo "working directory: ${S}"
  cd ${S}/git/LINUX
  ./configure --kernel-dir=${K} --kernel-sources=${K}
  #oe_runconf
}

do_compile() {
  # Uses ld instead of gcc to link.  Hack to remove linker flags.  Fix later!
  unset LDFLAGS
  cd ${S}/git/LINUX
  make -n
  #make -d
  make
  #oe_runmake
}

do_install() {
  # Don't know what to do here yet...
}

