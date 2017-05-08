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

You can run the (3 whole) tests with `lein test`.

## Examples

POST any string to the running service. You will get back in the body a key.
GET at <url>/key will give you your string back (with some tasteful alterations).

POST to any <url>/key to override that value!

## Addendum

If you've found this page while searching for a specific code challenge I may be administrating, 
Congratulations! You've proven your ability to google around for existing answers to problems. You are welcome to look through our solution to the challenge problem, but we will still be looking for some creativity on your part.

## License

Copyright © 2017 Andrew Ballinger

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
