#!/bin/sh
exec scala "$0" "$@"
!#

println("Hello, " + args(0) + "!")
