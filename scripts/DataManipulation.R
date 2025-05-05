# Data manipulation script

# All used libraries
library(tidyverse)

# Function doing everything
readCSV <- function() {
  # Import data
  data<-read.csv("data/data.csv")

  # Change classification column to numerical values
  data<-data[1:32] |>
    rename(class=diagnosis) |>
    mutate(class=ifelse(class=="M",1,0)) |>
    relocate(class,.after=last_col())

  # Find the best correlating arguments to the class
  correlations<-data |>
    select(-class) |>
    summarise(across(everything(),~cor(.x,data$class,use="complete.obs"))) |>
    pivot_longer(everything(),names_to="variable",values_to="correlation") |>
    arrange(desc(abs(correlation)))

  # Find the best 4 correlating arguments to the class
  top_correlated_vars<-correlations |>
    slice_max(order_by=abs(correlation),n=4) |>
    pull(variable)

  filtered_data<-data |>
    select(all_of(top_correlated_vars),class)

  # Save the filtered data to a new file
  write.csv(filtered_data,"data/filtered_data.csv",row.names=F)
}

readCSV()