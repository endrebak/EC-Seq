import pandas as pd


toptables = ["eciivsd", "mecvslec", "ovsy", "toptable"]


rule add_header:
    input:
        "../data/{toptable}.csv"
    output:
        "data/{toptable}_header.csv"
    run:
        df = pd.read_table(input[0], sep=" ")

        if wildcards.toptable != "toptable":
            df.columns = (wildcards.toptable + "_" + c  for c in df.columns)

        df.columns = (c.replace(".", "_").lower()  for c in df.columns)

        df.to_csv(output[0], sep=" ")


rule join_toptables:
    input:
        expand("data/{toptable}_header.csv", toptable=toptables)
    output:
        "data/joined.csv"
    run:
        tables = (pd.read_table(f, sep=" ", index_col=0) for f in input)
        df = pd.concat(tables, axis=1)
        print(df.head(2))
        df.to_csv(output[0], sep=" ")
