# paste-bin

Little pastebin application.
However, since pastebins can be used for EVIL, this pastebin filters out any NAUGHTY WORDS you naughty lad/lass you.

## Installation

Requires leiningen to build:
https://leiningen.org/

## Usage

Running locally in dev mode is as easy as `lein run 3000` to listen on port 3000.

Otherwise you can build a jar with
`lein compile`
followed by 
`lein uberjar`.

## Examples

POST any string to the running service. You will get back in the body a key.
GET at <url>/key will give you your string back (with some tasteful alterations).

POST to any <url>/key to override that value!

## License

Copyright © 2017 Andrew Ballinger

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
