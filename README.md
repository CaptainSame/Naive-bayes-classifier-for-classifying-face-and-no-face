# Naive-bayes-classifier-for-classifying-face-and-no-face

Created by : Sameer Sharma

**Description**

Naive Bayes classifier is trained on the basis of 70x60 = 4200 features which correspond to the pixel values given in the dataset.
The dataset is evenly distributed with almost equal instances of positive and negative outcomes. Conditional probabilities that the 
outcome is positive or negative given the pixel value is space(" ") or hash(#) were calculated for each pixel. Each pixel is assumed 
to be independent of each other which enables us to multiply the probability values to get the resultant probabiliity. 

**Confusion Matrix**

Confusion matrix was created after labelling the test dataset. 
Total examples: 150
True Positives: 64
True Negatives: 69
False Positives: 8
False Negatives: 9
