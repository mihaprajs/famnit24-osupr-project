# KNN script in R

# Libraries
library(tidyverse)
library(caTools)
library(class)

data<-read.csv("data/data.csv")
data<-data[1:32] |>
  rename(class=diagnosis) |>
  mutate(class=ifelse(class=="M",1,0)) |>
  relocate(class,.after=last_col())
set.seed(255)
split<-sample.split(data$class,SplitRatio=0.8)

trainSet<-subset(data,split==T)
testSet<-subset(data,split==F)

trainSetScaled<-scale(trainSet[-32])
testSetScaled<-scale(testSet[-32])

prediction<-knn(train=trainSetScaled,
                test=testSetScaled,
                cl=trainSet$class,
                k=3)
actualClass<-testSet$class
confusionMatrix<-table(actualClass,prediction)
confusionMatrix

accuracy<-sum(diag(confusionMatrix))/length(actualClass)
sprintf("Accuracy: %.2f%%",accuracy*100)