Hyena Tokenize
=============
A word tokenizer built in Clojure, designed with document classification in mind.

**jwolohan@indiana.edu**

## Usage

The namespace is `hyena-tokenize.core`.

The workhorse function is `tokenize`.

```Clojure
(require '[hyena-tokenize.core :as hyena])
(hyena/tokenize "Ninjas are scary!!")
;; ["ninjas" "are" "scary" "!!"]
```

The script can also be run on a file. It expects one sentence/document per line.
```Bash
echo "Are ninjas scary??" > myfile.txt
lein run myfile.txt
# are ninjas scary ??
```

## Documentation

Tests are included in `test/hyena_tokenize/core_tests.clj`.

## License

Copyright © 2018 J.T. Wolohan

Distributed under the Mozilla Public License version 2.0.
