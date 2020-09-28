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
    result <- remove_values(result)
    result <- map_size_values(result)
    result
}

load_data_file <- function (file, row_names, version = 0, sha = "") {
  if(missing(row_names)) {
    row_names <- c("Invocation", "Iteration", "Value", "Unit", "Criterion",
                   "Benchmark", "VM", "Approach", "Extra", "Cores", "InputSize",
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
  bench$Approach <- factor(bench$Approach)
  
  bench <- mutate(bench, version = version, sha = sha)
  bench
}

remove_values <- function(data) {
  result <- filter(data, Benchmark == "benchmark.BankBenchmark" & Var <= 1581
                   | Benchmark == "benchmark.BankBenchmark2")
  result
}

map_size_values <- function(data) {
  data$Var <- map_values(data$Var)
  data
}

map_names <- function(old_names, name_map) {
  for (i in 1:length(old_names)) {
    old_name <- old_names[[i]]
    if (!is.null(name_map[[old_name]])) {
      old_names[i] <- name_map[[old_name]]
    }
  }
  old_names
}

map_values <- function(old) {

  for(i in 1:length(old)) {
    val <- old[i]
    repl <- 0
    if(val == 1000) {
      repl <- 1.0
    } else if(val == 1224) {
      repl <- 1.5
    } else if(val == 1414) {
      repl <- 2.0
    } else if(val == 1581) {
      repl <- 2.5
    } else if(val == 1732) {
      repl <- 3.0
    } else if(val == 1870) {
      repl <- 3.5
    } else if(val == 2000) {
      repl <- 4.0
    } else if(val == 2121) {
      repl <- 4.5
    } else if(val == 2236) {
      repl <- 5.0
    } else if(val == 2345) {
      repl <- 5.5
    } else if(val == 2449) {
      repl <- 6.0
    }
    old[i] <- repl
  }
  old
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
              aes(x = Var, #factor(Var),
                  y = RuntimeFactor, #stats$Value,
                  ymax = RuntimeFactor + RR.sd,
                  ymin = RuntimeFactor - RR.sd,
                  fill = Approach,
                  group = Approach,
                  colour = Approach)) +
    labs(x = "Million of Iterations", y = "Runtime Factor normalized to Classic 2020") +
    theme_bw() +
    theme(axis.text.x = element_text(angle=80, hjust=1), legend.position = "top", legend.title = element_blank()) +
    facet_wrap(~ Benchmark, scales = "free") +
    scale_y_log10() +
    #geom_boxplot(position = "dodge")
    geom_bar(stat = "identity", position="dodge") +
    geom_errorbar(stat = "identity", position = position_dodge(), color = "black") # +
    #geom_point(position = position_dodge(), aes(y=RR.median), color = "black")
  p
}

data <- load_all_data(folder, fileName)
data <- droplevels(
  subset(data,
         select = c(Value, Unit, Benchmark, VM, Approach, Iteration, Var, version, sha)))

# separate the baseline from the rest
df_baseline <- data %>% filter(Approach == "test-objectteams-classic-38")

# calculate the mean of the baseline value for each Benchmark-Var
df_baseline <- df_baseline %>% 
  group_by(Benchmark, Var) %>% 
  summarise(Value_baseline = mean(Value)) %>% 
  ungroup()

norm <- data %>% 
  left_join(df_baseline, by = c("Benchmark", "Var")) %>% 
  mutate(RuntimeRatio = Value / Value_baseline) %>%
  mutate(RuntimeRatio2 = Value_baseline / Value) %>%
  select(-Value_baseline)
# Calculate your ratio
#norm <- df_compare %>%
#  mutate(RuntimeRatio = Value / Value_baseline)

stats <- norm %>%
  group_by(VM, Benchmark, Approach, Var, version, sha) %>% # Value
  summarise(
    Time.ms = mean(Value),
    sd      = sd(Value),
    RuntimeFactor = mean(RuntimeRatio),
    RuntimeFactor2 = mean(RuntimeRatio2),
    RR.sd         = sd(RuntimeRatio),
    RR.median     = median(RuntimeRatio))

name_map <- list("test-objectteams-classic-25" = "Classic 2019",
                 "test-objectteams-classic-38" = "Classic 2020",
                 "test-objectteams-indy-25" = "Dispatch Plans",
                 "test-objectteams-indy-38" = "Polymorphic Dispatch Plans",
                 "benchmark.BankBenchmark" = "Dynamic Suite",
                 "benchmark.BankBenchmark2" = "Static Suite")

name_map2 <- list("benchmark.BankBenchmark" = "Dynamic Benchmark",
                  "benchmark.BankBenchmark2" = "Static Benchmark")

# Rename
levels(stats$Approach)  <- map_names(
  levels(stats$Approach),
  name_map)

stats$Benchmark  <- map_names(
  stats$Benchmark,
  name_map2)

stats <- filter(stats, Approach != "Classic 2020")

print(plot_data(stats))

