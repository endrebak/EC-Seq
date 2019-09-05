import pandas as pd

dfs = []
for f in "eciivsd.csv  mecvslec.csv  ovsy.csv".split():
    df = pd.read_csv(f, sep="\s+")
    df.insert(df.shape[1], "Comparison", f.split(".")[0])
    dfs.append(df)

large_df = pd.concat(dfs)
large_df.to_csv("concat.csv", sep="\t", index=False)
