import pandas as pd
import numpy as np
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report, confusion_matrix
from sklearn.linear_model import LogisticRegression

df = pd.read_csv("D:/Match.csv")
df.head(5)

relevant_df = df[df.columns[df.columns.isin(['season', 'home_team_goal', 'away_team_goal', 'B365H',	'B365D',	'B365A',	'BWH',	'BWD',	'BWA',	'IWH',	'IWD',	'IWA',	'LBH',	'LBD',	'LBA','WHH',	'WHD',	'WHA',	'SJH',	'SJD',	'SJA',	'VCH',	'VCD',	'VCA',	'GBH',	'GBD',	'GBA',	'BSH',	'BSD',	'BSA'])]]

relevant_df['predict'] = np.where(df['home_team_goal'] == df['away_team_goal'], '0', np.where(df['home_team_goal'] > df['away_team_goal'], '1', '-1'))
relevant_df = relevant_df[['season', 'home_team_goal', 'away_team_goal', 'predict', 'B365H',	'B365D',	'B365A',	'BWH',	'BWD',	'BWA',	'IWH',	'IWD',	'IWA',	'LBH',	'LBD',	'LBA','WHH',	'WHD',	'WHA',	'SJH',	'SJD',	'SJA',	'VCH',	'VCD',	'VCA',	'GBH',	'GBD',	'GBA',	'BSH',	'BSD',	'BSA']]

s = pd.Series(relevant_df['season'])
labels, levels = pd.factorize(s)
relevant_df['season'] = labels

training_set = relevant_df[relevant_df['season'] != 7]

# fill missing values
print(training_set.dtypes)
for column in training_set:
    if training_set[column].dtype == 'float64':
        training_set[column].fillna(training_set[column].mean(), inplace=True)

test_set = relevant_df[relevant_df['season'] == 7]

for column in test_set:
    if test_set[column].dtype == 'float64':
        test_set[column].fillna(training_set[column].mean(), inplace=True)


training_set = training_set.values
test_set = test_set.values

a=13
b=16


feature_training = training_set[:, a:b]
prediction_training = training_set[:, 3]

features = relevant_df.iloc[:, a:b].values.reshape(-1, 1)
predict = relevant_df.iloc[:, 3].values.reshape(-1, 1)

feature_test = test_set[:, a:b]
predict_test = test_set[:, 3]

# fit

clf = KNeighborsClassifier(n_neighbors=350)
clf.fit(feature_training, prediction_training)


# predict

y_pred = clf.predict(feature_test)

print(confusion_matrix(predict_test, y_pred))
print(classification_report(predict_test, y_pred))