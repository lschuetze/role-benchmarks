library("plyr", character.only=TRUE)
library("dplyr", character.only=TRUE)
library("ggplot2", character.only=TRUE)
library("psych", character.only=TRUE)
library("tables", character.only=TRUE)
library("reshape2", character.only=TRUE)
library("assertthat", character.only=TRUE)
library("scales", character.only=TRUE)
library("memoise", character.only=TRUE)
library("RColorBrewer", character.only=TRUE)
library("ggrepel", character.only=TRUE)

# avoid scientific notation for numbers, it's more readable to me
options(scipen=999)

folder <- "/Users/lschuetze/Development/repos/role-benchmarks/data"
fileName <- "benchmark.data"
load_all_data <- function (folder, fileName) {
    result <- NULL
    folders <- sort(list.files(path=folder, "[0-9]+"))
    for(f in folders) {
      v <- strsplit(f, "-")
      data <- load_data_file(paste(folder, f, fileName, sep="/"),
                             version = as.numeric(v[[1]][1]),
                             sha     = v[[1]][2])
      result <- rbind(result, data)
    }
    result$sha <- factor(result$sha)
    result
}

load_data_file <- function (file, row_names, version = 0, sha = "") {
  if(missing(row_names)) {
    row_names <- c("Invocation", "Iteration", "Value", "Unit", "Criterion",
                   "Benchmark", "VM", "Suite", "Extra", "Cores", "InputSize",
                   "Var")
  }
  
  bench <- read.table(file, sep="\t", header=FALSE, col.names=row_names, fill=TRUE)
  
  # Give Run Ids to the rows, but need to consider different criterions
  num_criteria <- length(levels(bench$Criterion))
  if (num_criteria < 1) {
    num_criteria = 1
  }
  
  run_nums <- seq_len(nrow(bench) / num_criteria)
  bench$rid <- rep(run_nums, each = num_criteria)
  
  bench$Extra <- factor(bench$Extra)
  bench$Cores <- factor(bench$Cores)
  bench$Suite <- factor(bench$Suite)
  
  bench <- mutate(bench, version = version, sha = sha)
  bench
}

theme_simple <- function() {
  theme_bw() +
    theme(axis.text.x          = element_text(size = 12, lineheight=0.7),
          axis.title.x         = element_blank(),
          axis.title.y         = element_text(size = 12),
          axis.text.y          = element_text(size = 12),
          axis.line            = element_line(colour = "gray"),
          plot.title           = element_text(size = 12),
          panel.background     = element_blank(), #element_rect(fill = NA, colour = NA),
          panel.grid.major     = element_blank(),
          panel.grid.minor     = element_blank(),
          panel.border         = element_blank(),
          plot.background      = element_blank(), #element_rect(fill = NA, colour = NA)
          strip.background     = element_blank(),
          plot.margin = unit(c(0,0,0,0), "cm")) 
}

plot_data <- function(stats) {
  p <- ggplot(data = stats,
              aes(x = Var,
                  y = RuntimeFactor,
                  ymax = RuntimeFactor + RR.sd,
                  ymin = RuntimeFactor - RR.sd,
                  fill = Suite)) +
    facet_wrap(~ Benchmark, scales = "free") +
    #scale_y_log10() +
    geom_bar(stat="identity",position="dodge") +
    geom_errorbar(position = position_dodge()) +
    geom_point(position=position_dodge(), aes(y=RR.median, colour=Suite))
  p
}

data <- load_all_data(folder, fileName)
data <- droplevels(
  subset(data,
         select = c(Value, Unit, Benchmark, VM, Suite, Iteration, Var, version, sha)))

# separate the baseline from the rest
df_baseline <- data %>% filter(Suite == "test-objectteams-classic-38")
df_compare  <- data %>% filter(Suite != "test-objectteams-classic-38")

# calculate the mean of the baseline value for each Benchmark-Var
df_baseline <- df_baseline %>% 
  group_by(Benchmark, Var) %>% 
  summarise(Value_baseline = mean(Value)) %>% 
  ungroup()

# Join the baseline data to the rest of your data with the approaches
#df_compare <- df_compare %>%
#  left_join(df_baseline, by = c("Benchmark", "Var"))
norm <- data %>% 
  left_join(df_baseline, by = c("Benchmark", "Var")) %>% 
  mutate(RuntimeRatio = Value / Value_baseline) %>% 
  select(-Value_baseline)
# Calculate your ratio
#norm <- df_compare %>%
#  mutate(RuntimeRatio = Value / Value_baseline)

stats <- norm %>%
  group_by(VM, Benchmark, Suite, Var, version, sha) %>%
  summarise(
    Time.ms = mean(Value),
    sd      = sd(Value),
    RuntimeFactor = mean(RuntimeRatio),
    RR.sd         = sd(RuntimeRatio),
    RR.median     = median(RuntimeRatio))

print(plot_data(stats))

