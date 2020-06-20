import pandas as pd
import numpy as np
from sklearn import preprocessing
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report, confusion_matrix
from sklearn.tree import DecisionTreeClassifier

df = pd.read_csv("Match.csv")
df.head(5)

relevant_df = df[df.columns[df.columns.isin(['season', 'home_team_goal', 'away_team_goal', 'B365H',	'B365D',	'B365A',	'BWH',	'BWD',	'BWA',	'IWH',	'IWD',	'IWA',	'LBH',	'LBD',	'LBA','WHH',	'WHD',	'WHA',	'SJH',	'SJD',	'SJA',	'VCH',	'VCD',	'VCA',	'GBH',	'GBD',	'GBA',	'BSH',	'BSD',	'BSA'])]]

relevant_df['predict'] = np.where(df['home_team_goal'] == df['away_team_goal'], '0', np.where(df['home_team_goal'] > df['away_team_goal'], '1', '-1'))
relevant_df = relevant_df[['season', 'home_team_goal', 'away_team_goal', 'predict', 'B365H',	'B365D',	'B365A',	'BWH',	'BWD',	'BWA',	'IWH',	'IWD',	'IWA',	'LBH',	'LBD',	'LBA','WHH',	'WHD',	'WHA',	'SJH',	'SJD',	'SJA',	'VCH',	'VCD',	'VCA',	'GBH',	'GBD',	'GBA',	'BSH',	'BSD',	'BSA']]

s = pd.Series(relevant_df['season'])
labels, levels = pd.factorize(s)
relevant_df['season'] = labels


training_set = relevant_df[relevant_df['season'] != 7]

print(training_set.dtypes)
for column in training_set:
    if training_set[column].dtype == 'float64':
        training_set[column].fillna(training_set[column].mean(), inplace=True)


test_set = relevant_df[relevant_df['season'] == 7]

for column in test_set:
    if test_set[column].dtype == 'float64':
        test_set[column].fillna(training_set[column].mean(), inplace=True)

i=4
while(i<31):
    min=training_set.iloc[:, i].min()
    max=training_set.iloc[:, i].max()
    if(min==max):
        training_set.iloc[:, i] = "0"
        i=i+1
        continue
    training_set.iloc[:, i] = (training_set.iloc[:, i] - min) / (max - min)
    training_set.iloc[:, i] = pd.cut(training_set.iloc[:, i], bins=5, labels=False)
    i = i + 1

i=4
while(i<31):
    min=test_set.iloc[:, i].min()
    max=test_set.iloc[:, i].max()
    if(min==max):
        test_set.iloc[:, i] = "0"
        i=i+1
        continue
    test_set.iloc[:, i] = (test_set.iloc[:, i] - min) / (max - min)
    test_set.iloc[:, i] = pd.cut(test_set.iloc[:, i], bins=5, labels=False)
    i = i + 1

training_set = training_set.values
test_set = test_set.values

a=4
b=7

feature_training = training_set[:, a:b]
prediction_training = training_set[:, 3]

features = relevant_df.iloc[:, a:b].values.reshape(-1, 1)
predict = relevant_df.iloc[:, 3].values.reshape(-1, 1)

feature_test = test_set[:, a:b]
predict_test = test_set[:, 3]

# fit

clf = DecisionTreeClassifier(criterion="gini", random_state=100, max_depth=10, min_samples_leaf=5)
clf.fit(feature_training, prediction_training)

# predict

y_pred = clf.predict(feature_test)

print(confusion_matrix(predict_test, y_pred))
print(classification_report(predict_test, y_pred))
