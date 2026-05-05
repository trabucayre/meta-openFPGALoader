# meta-openFPGALoader

Yocto/OpenEmbedded layer providing a recipe for
[openFPGALoader](https://github.com/trabucayre/openFPGALoader), a command-line
utility for programming FPGA devices.

This layer is intended for users who already have a working Yocto build
environment.

## Compatibility

- Yocto release: `scarthgap` / 5.0 LTS
- Layer collection: `openFPGALoader`
- Layer dependencies:
  - `core`
  - `openembedded-layer` from `meta-openembedded/meta-oe`

The `meta-oe` dependency is required for packages used by optional
openFPGALoader features, such as `libftdi`, `hidapi`, and `libgpiod`.

## Prepare The Yocto Environment

Clone this layer next to the other layers used by your build:

```bash
git clone https://github.com/trabucayre/meta-openFPGALoader.git
```

If your build does not already include `meta-openembedded`, add the matching
Yocto branch:

```bash
git clone -b scarthgap https://github.com/openembedded/meta-openembedded.git
```

From an initialized Yocto build directory, add the required layers:

```bash
bitbake-layers add-layer /somewhere/meta-openembedded/meta-oe
bitbake-layers add-layer /somewhere/meta-openFPGALoader
```

Equivalent `conf/bblayers.conf` entries are:

```bitbake
BBLAYERS += " \
    /somewhere/meta-openembedded/meta-oe \
    /somewhere/meta-openFPGALoader \
"
```

## Add openFPGALoader To An Image

Add the package to your image from `conf/local.conf`, a distro configuration,
or your own image recipe:

```bitbake
IMAGE_INSTALL:append = " openfpgaloader"
```

Then build your image as usual:

```bash
bitbake <image-name>
```

To build only the recipe:

```bash
bitbake openfpgaloader
```

## Recipe Overview

The layer currently provides one recipe:

- `recipes-devtools/openfpgaloader/openfpgaloader_1.1.1.bb`

The recipe builds openFPGALoader from the upstream Git repository at the v1.1.1
revision.

The recipe uses CMake and exposes openFPGALoader cable, vendor, USB scan, and
udev support through `PACKAGECONFIG`.

By default, the recipe enables all listed cable and vendor backends, USB scan
support, and udev rule installation.

## Configure Recipe Features

Override `PACKAGECONFIG` when you want a smaller binary or when your image does
not need every cable and FPGA vendor backend.

Example for a reduced build with FTDI-based cables, USB Blaster I, CMSIS-DAP,
iCE40, Xilinx, and udev integration:

```bitbake
PACKAGECONFIG:pn-openfpgaloader = " \
    cable-ftdi \
    cable-usb-blaster1 \
    cable-cmsisdap \
    vendor-ice40 \
    vendor-xilinx \
    udev \
"
```

Use `PACKAGECONFIG:append:pn-openfpgaloader` or
`PACKAGECONFIG:remove:pn-openfpgaloader` if you only need to adjust the default
set:

```bitbake
PACKAGECONFIG:remove:pn-openfpgaloader = "cable-jlink cable-gpiod"
```

## PACKAGECONFIG Options

Cable backends:

- `cable-anlogic`: Anlogic USB cable support, depends on `libusb1`
- `cable-ch347`: WCH CH347 cable support, depends on `libusb1`
- `cable-cmsisdap`: CMSIS-DAP support, depends on `hidapi`
- `cable-dirtyjtag`: DirtyJTAG support, depends on `libusb1`
- `cable-dfu`: DFU support, depends on `libusb1`
- `cable-esp-usb`: ESP USB support, depends on `libusb1`
- `cable-ftdi`: FTDI-based cable support, depends on `libftdi`
- `cable-gpiod`: GPIO bit-bang support through libgpiod, depends on `libgpiod`
- `cable-gowin-gwu2x`: Gowin GWU2X support, depends on `libusb1`
- `cable-jlink`: SEGGER J-Link support, depends on `libusb1`
- `cable-remotebitbang`: remote bit-bang support
- `cable-svf`: SVF JTAG support
- `cable-usb-blaster1`: Altera USB Blaster I support, depends on `libftdi`
- `cable-usb-blaster2`: Altera USB Blaster II support, depends on `libftdi`
- `cable-xvc-client`: Xilinx Virtual Cable client support
- `cable-xvc-server`: Xilinx Virtual Cable server support

FPGA vendor backends:

- `vendor-altera`: Altera support, depends on `zlib`
- `vendor-anlogic`: Anlogic support
- `vendor-colognechip`: Cologne Chip support, depends on `libftdi`
- `vendor-efinix`: Efinix support, depends on `libftdi`
- `vendor-gowin`: Gowin support
- `vendor-ice40`: iCE40 support, depends on `libftdi`
- `vendor-lattice`: Lattice support
- `vendor-latticesspi`: Lattice SSPI support, depends on `libftdi`
- `vendor-xilinx`: Xilinx support

System integration:

- `usb-scan`: USB device scan support, depends on `libusb1`
- `udev`: install udev rules, depends on `udev`

## Notes

- `libftdi`, `hidapi`, and `libgpiod` are provided by
  `meta-openembedded/meta-oe`, so `meta-oe` must be present when the
  corresponding `PACKAGECONFIG` options are enabled.
- For target access to USB/JTAG devices, make sure your runtime user has the
  expected device permissions.

## Contact

E-mail: Gwenhael Goavec-Merou <gwenhael.goavec-merou@trabucayre.com></br>
Copyright (C) <b>2026</b></br>
SPDX-License-Identifier: Apache-2.0</br>