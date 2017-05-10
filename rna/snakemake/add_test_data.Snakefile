template_string = """INSERT INTO RNADB
(id, FDR, contrast1, contrast2, contrast3, info)
VALUES ("{id}", "{FDR}", "{contrast1}", "{contrast2}", "{contrast3}", "{info}");
"""

test_data = [["gen1", "0.05", "0.1", "0.2", "0.3", "Litt info"],
             ["gen2", "0.81", "0.01", "0.02", "0.03", "Litt mer info 2"]]

rule create_test_data:
    output:
        "data/test.sql"
    run:
        outstrings = []
        for gene_id, fdr, contrast1, contrast2, contrast3, info in test_data:
            sql_statement = template_string.format(id=gene_id, FDR=fdr,
                                                   contrast1=contrast1,
                                                   contrast2=contrast2,
                                                   contrast3=contrast3,
                                                   info=info)
            outstrings.append(sql_statement)

        open(output[0], "w+").write("\n".join(outstrings))


rule run_mysql:
    input:
        "data/test.sql"
    output:
        "data/token.file"
    shell:
        "mysql -u endrebak -p -D rna_dev < {input[0]} > {output[0]}"
