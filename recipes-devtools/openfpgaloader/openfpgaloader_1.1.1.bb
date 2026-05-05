SUMMARY = "Universal utility for programming FPGA"
HOMEPAGE = "https://github.com/trabucayre/openFPGALoader"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/trabucayre/openFPGALoader.git;branch=master;protocol=https \
           file://0001-usbBlaster-fix_ifdef_loc.patch \
          "
# Tag v1.1.1
SRCREV = "85be4fa02b2dd6a83716d7dfac3d25bbd260ff7b"

S = "${WORKDIR}/git"

FILES:${PN} += "${datadir}/openFPGALoader"

inherit cmake pkgconfig

# No defaults/hardcoded dependencies. These are deduced to cables/vendors enabled.
DEPENDS = " "

# Disable all cables/vendors by default. PACKAGECONFIG entries take full control.
EXTRA_OECMAKE = " \
    -DENABLE_CABLE_ALL=OFF \
    -DENABLE_VENDORS_ALL=OFF \
    -DUSE_PKGCONFIG=ON \
    -DBUILD_STATIC=OFF \
    -DCMAKE_DISABLE_FIND_PACKAGE_LibFTDI1=ON \
"

# With ninja fails due to sysroot-stripping issue
OECMAKE_GENERATOR = "Unix Makefiles"

# ----------------------------------------------------------------------------
# PACKAGECONFIG -- one entry per cable or vendor target.
# Add/remove entries to select exactly what gets compiled in.
# Dependencies are deduced automatically from the active entries.
#
# Example: keep only FTDI cables and Xilinx/iCE40 targets
#   PACKAGECONFIG:pn-openfpgaloader = " \
#       cable-ftdi cable-usb-blaster1 \
#       vendor-ice40 vendor-xilinx \
#       udev \
#   "
# ----------------------------------------------------------------------------
PACKAGECONFIG ??= " \
    cable-anlogic \
    cable-ch347 \
    cable-cmsisdap \
    cable-dirtyjtag \
    cable-dfu \
    cable-esp-usb \
    cable-ftdi \
    cable-gpiod \
    cable-gowin-gwu2x \
    cable-jlink \
    cable-remotebitbang \
    cable-svf \
    cable-usb-blaster1 \
    cable-usb-blaster2 \
    cable-xvc-client \
    cable-xvc-server \
    vendor-altera \
    vendor-anlogic \
    vendor-colognechip \
    vendor-efinix \
    vendor-gowin \
    vendor-ice40 \
    vendor-lattice \
    vendor-latticesspi \
    vendor-xilinx \
    usb-scan \
    udev \
"

# -- Cables ------------------------------------------------------------------
PACKAGECONFIG[cable-anlogic]      = "-DENABLE_ANLOGIC_CABLE=ON,             -DENABLE_ANLOGIC_CABLE=OFF,               libusb1,"
PACKAGECONFIG[cable-ch347]        = "-DENABLE_CH347=ON,                      -DENABLE_CH347=OFF,                      libusb1,"
PACKAGECONFIG[cable-cmsisdap]     = "-DENABLE_CMSISDAP=ON,                   -DENABLE_CMSISDAP=OFF,                   hidapi,"
PACKAGECONFIG[cable-dirtyjtag]    = "-DENABLE_DIRTYJTAG=ON,                  -DENABLE_DIRTYJTAG=OFF,                  libusb1,"
PACKAGECONFIG[cable-dfu]          = "-DENABLE_DFU=ON,                        -DENABLE_DFU=OFF,                        libusb1,"
PACKAGECONFIG[cable-esp-usb]      = "-DENABLE_ESP_USB=ON,                    -DENABLE_ESP_USB=OFF,                    libusb1,"
PACKAGECONFIG[cable-ftdi]         = "-DENABLE_FTDI_BASED_CABLE=ON,           -DENABLE_FTDI_BASED_CABLE=OFF,           libftdi,"
PACKAGECONFIG[cable-gpiod]        = "-DENABLE_LIBGPIOD=ON,                   -DENABLE_LIBGPIOD=OFF,                   libgpiod,"
PACKAGECONFIG[cable-gowin-gwu2x]  = "-DENABLE_GOWIN_GWU2X=ON,                -DENABLE_GOWIN_GWU2X=OFF,                libusb1,"
PACKAGECONFIG[cable-jlink]        = "-DENABLE_JLINK=ON,                      -DENABLE_JLINK=OFF,                      libusb1,"
PACKAGECONFIG[cable-remotebitbang]= "-DENABLE_REMOTEBITBANG=ON,              -DENABLE_REMOTEBITBANG=OFF,              ,"
PACKAGECONFIG[cable-svf]          = "-DENABLE_SVF_JTAG=ON,                   -DENABLE_SVF_JTAG=OFF,                   ,"
PACKAGECONFIG[cable-usb-blaster1] = "-DENABLE_USB_BLASTERI=ON,               -DENABLE_USB_BLASTERI=OFF,               libftdi,"
PACKAGECONFIG[cable-usb-blaster2] = "-DENABLE_USB_BLASTERII=ON,              -DENABLE_USB_BLASTERII=OFF,              libftdi,"
PACKAGECONFIG[cable-xvc-client]   = "-DENABLE_XILINX_VIRTUAL_CABLE_CLIENT=ON,-DENABLE_XILINX_VIRTUAL_CABLE_CLIENT=OFF,,"
PACKAGECONFIG[cable-xvc-server]   = "-DENABLE_XILINX_VIRTUAL_CABLE_SERVER=ON,-DENABLE_XILINX_VIRTUAL_CABLE_SERVER=OFF,,"

# -- Vendor targets ----------------------------------------------------------
PACKAGECONFIG[vendor-altera]      = "-DENABLE_ALTERA_SUPPORT=ON,      -DENABLE_ALTERA_SUPPORT=OFF,      zlib,"
PACKAGECONFIG[vendor-anlogic]     = "-DENABLE_ANLOGIC_SUPPORT=ON,     -DENABLE_ANLOGIC_SUPPORT=OFF,     ,"
PACKAGECONFIG[vendor-colognechip] = "-DENABLE_COLOGNECHIP_SUPPORT=ON, -DENABLE_COLOGNECHIP_SUPPORT=OFF, libftdi,"
PACKAGECONFIG[vendor-efinix]      = "-DENABLE_EFINIX_SUPPORT=ON,      -DENABLE_EFINIX_SUPPORT=OFF,      libftdi,"
PACKAGECONFIG[vendor-gowin]       = "-DENABLE_GOWIN_SUPPORT=ON,       -DENABLE_GOWIN_SUPPORT=OFF,       ,"
PACKAGECONFIG[vendor-ice40]       = "-DENABLE_ICE40_SUPPORT=ON,       -DENABLE_ICE40_SUPPORT=OFF,       libftdi,"
PACKAGECONFIG[vendor-lattice]     = "-DENABLE_LATTICE_SUPPORT=ON,     -DENABLE_LATTICE_SUPPORT=OFF,     ,"
PACKAGECONFIG[vendor-latticesspi] = "-DENABLE_LATTICESSPI_SUPPORT=ON, -DENABLE_LATTICESSPI_SUPPORT=OFF, libftdi,"
PACKAGECONFIG[vendor-xilinx]      = "-DENABLE_XILINX_SUPPORT=ON,      -DENABLE_XILINX_SUPPORT=OFF,      ,"

# -- System integration ------------------------------------------------------
PACKAGECONFIG[usb-scan]           = "-DENABLE_USB_SCAN=ON,            -DENABLE_USB_SCAN=OFF,            libusb1,"
PACKAGECONFIG[udev]               = "-DENABLE_UDEV=ON,                -DENABLE_UDEV=OFF,                udev,"
