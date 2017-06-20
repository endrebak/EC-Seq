##########################################################################################
##SCRIPT TO PLOT EXPRESSION OF EC GENES
##########################################################################################

library("reshape2")
library("ggplot2")

#############################
##Plotting function
#############################

EC.plot.function3 <- function(exp.data.frame, genelist, no.columns, width.val, height.val, filename){
	df.sel <- exp.data.frame[exp.data.frame$variable %in% genelist,]
	df.sel$variable <- factor(df.sel$variable, levels=unique(genelist), ordered=TRUE)
  print(head(df.sel))
	ggsave(filename,
		ggplot(df.sel) + geom_boxplot(aes(x = Samples, y = value, color=paste(Layer, Region)), size = 0.2, outlier.size = 0.1) + facet_wrap(~variable, ncol = no.columns) +
			theme_bw(base_size=8) +
			scale_x_discrete(breaks=c("Lat.P02.D", "Lat.P09.D", "Lat.P23.D", "Lat.P45.D", "Med.P02.D", "Med.P09.D", "Med.P23.D", "Med.P45.D"),
			labels=c("Lat.P02.D" = "P02", "Lat.P09.D" = "P09", "Lat.P23.D" = "P23", "Lat.P45.D" = "P45", "Med.P02.D" = "P02", "Med.P09.D" = "P09", "Med.P23.D" = "P23", "Med.P45.D" = "P45")) +
			theme(axis.text.x  = element_text(angle=90, vjust=1, hjust=0, size=6), panel.grid.major = element_blank(), panel.grid.minor = element_blank(), legend.key = element_blank(), legend.key.size = unit(1, "lines"),
			legend.key.height = unit(0.8, "lines"), legend.spacing = unit(0.1, "cm"), legend.title = element_blank(), strip.background = element_rect(fill="white", size = 0.10, colour = "grey50"),
			strip.text.x = element_text(margin = margin(t = 2, b = 2)), axis.line = element_line(colour = "black", size = 0.10), panel.border = element_rect(fill = NA, colour = "grey50", size=0.10),
			legend.key.width = unit(0.6, "lines")) +
    ylab("log2 RPM") + coord_cartesian(ylim=c(min(0, min(df.sel$value)), max(4, max(df.sel$value)))),
    width = width.val, height = height.val)
}

#############################
##Load data
#############################

pd <- read.table(snakemake@input[[1]], sep=" ")

print(head(pd))

#############################
##Example
#############################

##Define genes of interest
Markers <- c(snakemake@wildcards[["gene"]]) # , "Calb1", "Sst", "Sstr1", "Sstr2", "Sstr3", "Sstr4", "Calb2", "Cck", "Cckar", "Cckbr", "Penk")
##Plot genes of interest
EC.plot.function3(pd, Markers, 4, 4.4, 3.3, snakemake@output[[1]])
