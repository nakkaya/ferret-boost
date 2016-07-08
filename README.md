ferret-boost
===============

Boost Bindigns For Ferret.

## Bindings

   - Asio Serial Port
   - String Utils

## Usage

Sample build configuration for Mac OS X,

    (configure-ferret! :compiler-options ["-std=c++11"
                                          "-I/usr/local/Cellar/boost/1.55.0/include/"
                                          "-L/usr/local/Cellar/boost/1.55.0/lib/"
                                          "-lboost_system-mt"])

### String Utils

   - split
   - replace
   - trim

### Asio Serial

Import the library,

    (require '[ferret-boost.serial :as serial])

Get a serial port handle,

    (def conn (serial/open "/dev/tty.usbmodem1441" 57600))

`open` returns nil on error or a handle to a serial connection
object.

Write a `byte`,

    (serial/write conn 0x90)

Read a `unsigned char` as a ferret `Number`,

    (serial/read conn)

Read line,

    (serial/read-line conn)

Close the port,

    (serial/close conn)

