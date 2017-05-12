"""
fit2.TMMQua <- readRDS("/local/home/lenecho/Seq_EC/results/LongRNA-rn6/CountsRn6_Gene/analyses/​fit2TMMQua.rds")

ECIIvsD <- topTable(fit2.TMMQua, coef = "ECIIvsDeep", p.value = 0.05, number = 20000)
OvsY <- topTable(fit2.TMMQua, coef = "ECYoungvsOld", p.value = 0.05, number = 20000)
MECvsLEC <- topTable(fit2.TMMQua, coef = "MedvsLat", p.value = 0.05, number = 20000)


ECIIvsD <- topTable(df, coef = "ECIIvsDeep", p.value = 1, number = Inf)
OvsY <- topTable(df, coef = "ECYoungvsOld", p.value = 1, number = Inf)
MECvsLEC <- topTable(df, coef = "MedvsLat", p.value = 1, number = Inf)
"""
