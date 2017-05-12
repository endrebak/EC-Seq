"""
Creates a plot for every single gene in Lenes dataframe

Downside: loads the dataframe each graph
Upside: can run in parallel easily
"""

import pandas as pd


df = pd.read_table("../data/pd_EC.csv", sep=" ")
genes = df.variable.drop_duplicates().reset_index(drop=True)


rule all:
    input:
        expand("data/plots/{gene}.png",
               gene=genes)

rule plot_genes:
    # requires ggplot2 and reshape2
    input:
        "../data/pd_EC.csv"
    output:
        "data/plots/{gene}.png"
    script:
        "scripts/plot_genes.R"
