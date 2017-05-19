# rna

FIXME

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run

## License

Copyright © 2017 FIXME

## Notes to self

First need to create databases

```
$ mysql -p
create database rna_dev;
create database rna_test;
```

Then can run

```
lein run migrate
```

Profiles

```
lein run # starts development profile
```

Create test data and add it

```
snakemake -s snakemake/add_test_data.Snakefile -f snakemake/data/test.sql
mysql -p -u endrebak -D rna_dev < snakemake/data/test.sql
```

Run development server

```
lein run
```

Connect to website

```
lynx localhost:3000
```

Database port:

```
3306
```

Port for server:

```
8080
```
