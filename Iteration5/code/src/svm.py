import pandas as pd
import numpy as np
import heapq
import numpy
from sklearn.svm import SVC
from sklearn.metrics import classification_report, confusion_matrix

# read the files
df_player_att = pd.read_csv("D:/Player_Attributes.csv")
df_match = pd.read_csv("D:/Match.csv")

# prepare the date

relevant_df_player_att = df_player_att[
    df_player_att.columns[df_player_att.columns.isin(['player_api_id', 'crossing', 'finishing', 'heading_accuracy',
                                                      'short_passing', 'volleys', 'dribbling', 'curve',
                                                      'free_kick_accuracy', 'long_passing', 'ball_control',
                                                      'acceleration', 'sprint_speed', 'agility', 'reactions', 'balance',
                                                      'shot_power', 'jumping', 'stamina', 'strength',
                                                      'long_shots', 'aggression', 'interceptions', 'positioning',
                                                      'vision', 'penalties', 'marking', 'standing_tackle',
                                                      'sliding_tackle', 'gk_diving', 'gk_handling', 'gk_kicking',
                                                      'gk_positioning', 'gk_reflexes'])]]

relevant_df_match = df_match[df_match.columns[df_match.columns.isin(
    ['season', 'home_team_goal', 'away_team_goal', 'home_player_1', 'home_player_2', 'home_player_3',
     'home_player_4', 'home_player_5', 'home_player_6', 'home_player_7', 'home_player_8', 'home_player_9',
     'home_player_10', 'home_player_11', 'away_player_1', 'away_player_2', 'away_player_3', 'away_player_4',
     'away_player_5', 'away_player_6', 'away_player_7', 'away_player_8', 'away_player_9', 'away_player_10',
     'away_player_11'])]]

relevant_df_match['predict'] = np.where(relevant_df_match['home_team_goal'] == relevant_df_match['away_team_goal'], '1',
                                        np.where(
                                            relevant_df_match['home_team_goal'] > relevant_df_match['away_team_goal'],
                                            '1', '0'))
relevant_df_match = relevant_df_match[
    ['season', 'home_team_goal', 'away_team_goal', 'predict', 'home_player_1', 'home_player_2', 'home_player_3', 'home_player_4',
     'home_player_5', 'home_player_6', 'home_player_7', 'home_player_8',
     'home_player_9', 'home_player_10', 'home_player_11', 'away_player_1', 'away_player_2',
     'away_player_3', 'away_player_4', 'away_player_5', 'away_player_6', 'away_player_7',
     'away_player_8', 'away_player_9', 'away_player_10', 'away_player_11']]

s = pd.Series(relevant_df_match['season'])
labels, levels = pd.factorize(s)
relevant_df_match['season'] = labels

# fill missing values

for column in relevant_df_player_att:
    if relevant_df_player_att[column].dtype == 'float64':
        relevant_df_player_att[column].fillna(relevant_df_player_att[column].mean(), inplace=True)
# creating the relevant vectors of the function
relevant_df_player_att = relevant_df_player_att.groupby("player_api_id").mean().round().reset_index()

# relevant_df_player_att.to_csv('data mimi project/test.csv')
vector_player_att = relevant_df_player_att

vector_player_att['attack_vector'] = vector_player_att['crossing'] + vector_player_att['finishing'] \
                                     + vector_player_att['heading_accuracy'] + vector_player_att['short_passing'] \
                                     + vector_player_att['volleys']

vector_player_att['skill_vector'] = vector_player_att['dribbling'] + vector_player_att['curve'] \
                                    + vector_player_att['free_kick_accuracy'] + vector_player_att['long_passing'] \
                                    + vector_player_att['ball_control']

vector_player_att['movement_vector'] = vector_player_att['acceleration'] \
                                       + vector_player_att['sprint_speed'] + vector_player_att['agility'] \
                                       + vector_player_att['reactions'] + vector_player_att['balance']

vector_player_att['power_vector'] = vector_player_att['shot_power'] + vector_player_att['jumping'] \
                                    + vector_player_att['stamina'] + vector_player_att['strength'] \
                                    + vector_player_att['long_shots']

vector_player_att['mentaly_vector'] = vector_player_att['aggression'] \
                                      + vector_player_att['interceptions'] + vector_player_att['positioning'] \
                                      + vector_player_att['vision'] + vector_player_att['penalties']

vector_player_att['defending_vector'] = vector_player_att['marking'] + \
                                        vector_player_att['standing_tackle'] + vector_player_att['sliding_tackle']

vector_player_att['gk_vector'] = vector_player_att['gk_diving'] + vector_player_att['gk_handling'] + \
                                 vector_player_att['gk_kicking'] + vector_player_att['gk_positioning'] + \
                                 vector_player_att['gk_reflexes']

relevant_df_match = relevant_df_match.dropna()

# training

training_set = relevant_df_match[relevant_df_match['season'] != 7]

# home
home_att = list()
home_sk = list()
home_df = list()
home_move = list()
home_pow = list()
home_mental = list()
home_gk = list()

# away
away_att = list()
away_sk = list()
away_df = list()
away_move = list()
away_pow = list()
away_mental = list()
away_gk = list()

datasetHome = pd.DataFrame()



training_set['svn_home_att_vec'] = ""
training_set['svn_home_sk_vec'] = ""
training_set['svn_home_move_vec'] = ""
training_set['svn_home_pow_vec'] = ""
training_set['svn_home_mental_vec'] = ""
training_set['svn_home_df_vec'] = ""
training_set['svn_home_gk_vec'] = ""
training_set['svn_away_att_vec'] = ""
training_set['svn_away_sk_vec'] = ""
training_set['svn_away_move_vec'] = ""
training_set['svn_away_pow_vec'] = ""
training_set['svn_away_mental_vec'] = ""
training_set['svn_away_df_vec'] = ""
training_set['svn_away_gk_vec'] = ""

for index, row in training_set.iterrows():
    for column in training_set:
        if column != 'season' and column != 'home_team_goal' and column != 'away_team_goal':
            if not column.startswith('svn'):
                if ("home" in column):
                    id_Player = row[column]
                    player_info = relevant_df_player_att.loc[
                        relevant_df_player_att['player_api_id'] == np.int64(id_Player)]
                    home_att.append(player_info['attack_vector'].values[0])
                    home_sk.append(player_info['skill_vector'].values[0])
                    home_move.append(player_info['movement_vector'].values[0])
                    home_pow.append(player_info['power_vector'].values[0])
                    home_mental.append(player_info['mentaly_vector'].values[0])
                    home_df.append(player_info['defending_vector'].values[0])
                    home_gk.append(player_info['gk_vector'].values[0])

                if ("away" in column):
                    id_Player = row[column]
                    player_info = relevant_df_player_att.loc[
                        relevant_df_player_att['player_api_id'] == np.int64(id_Player)]
                    away_att.append(player_info['attack_vector'].values[0])
                    away_sk.append(player_info['skill_vector'].values[0])
                    away_move.append(player_info['movement_vector'].values[0])
                    away_pow.append(player_info['power_vector'].values[0])
                    away_mental.append(player_info['mentaly_vector'].values[0])
                    away_df.append(player_info['defending_vector'].values[0])
                    away_gk.append(player_info['gk_vector'].values[0])

    sumOne = sum(heapq.nlargest(4, home_att))
    training_set['svn_home_att_vec'][index] = sumOne
    training_set['svn_home_sk_vec'][index] = sum(heapq.nlargest(5, home_sk))
    training_set['svn_home_move_vec'][index] = sum(heapq.nlargest(5, home_move))
    training_set['svn_home_pow_vec'][index] = sum(heapq.nlargest(5, home_pow))
    training_set['svn_home_mental_vec'][index] = sum(heapq.nlargest(5, home_mental))
    training_set['svn_home_df_vec'][index] = sum(heapq.nlargest(4, home_df))
    training_set['svn_home_gk_vec'][index] = sum(heapq.nlargest(1, home_gk))
    training_set['svn_away_att_vec'][index] = sum(heapq.nlargest(4, away_att))
    training_set['svn_away_sk_vec'][index] = sum(heapq.nlargest(5, away_sk))
    training_set['svn_away_move_vec'][index] = sum(heapq.nlargest(5, away_move))
    training_set['svn_away_pow_vec'][index] = sum(heapq.nlargest(5, away_pow))
    training_set['svn_away_mental_vec'][index] = sum(heapq.nlargest(5, away_mental))
    training_set['svn_away_df_vec'][index] = sum(heapq.nlargest(4, away_df))
    training_set['svn_away_gk_vec'][index] = sum(heapq.nlargest(1, away_gk))

    home_att = list()
    home_sk = list()
    home_df = list()
    home_gk = list()
    home_move = list()
    home_pow = list()
    home_mental = list()
    away_att = list()
    away_sk = list()
    away_df = list()
    away_gk = list()
    away_move = list()
    away_pow = list()
    away_mental = list()
'''
# testing
'''
testing_set = relevant_df_match[relevant_df_match['season'] == 7]

testing_set['svn_home_att_vec'] = ""
testing_set['svn_home_sk_vec'] = ""
testing_set['svn_home_move_vec'] = ""
testing_set['svn_home_pow_vec'] = ""
testing_set['svn_home_mental_vec'] = ""
testing_set['svn_home_df_vec'] = ""
testing_set['svn_home_gk_vec'] = ""
testing_set['svn_away_att_vec'] = ""
testing_set['svn_away_sk_vec'] = ""
testing_set['svn_away_move_vec'] = ""
testing_set['svn_away_pow_vec'] = ""
testing_set['svn_away_mental_vec'] = ""
testing_set['svn_away_df_vec'] = ""
testing_set['svn_away_gk_vec'] = ""

for index, row in testing_set.iterrows():
    for column in testing_set:
        if column != 'season' and column != 'home_team_goal' and column != 'away_team_goal':
            if not column.startswith('svn'):
                if ("home" in column):
                    id_Player = row[column]
                    player_info = relevant_df_player_att.loc[
                        relevant_df_player_att['player_api_id'] == np.int64(id_Player)]
                    home_att.append(player_info['attack_vector'].values[0])
                    home_sk.append(player_info['skill_vector'].values[0])
                    home_move.append(player_info['movement_vector'].values[0])
                    home_pow.append(player_info['power_vector'].values[0])
                    home_mental.append(player_info['mentaly_vector'].values[0])
                    home_df.append(player_info['defending_vector'].values[0])
                    home_gk.append(player_info['gk_vector'].values[0])

                if ("away" in column):
                    id_Player = row[column]
                    player_info = relevant_df_player_att.loc[
                        relevant_df_player_att['player_api_id'] == np.int64(id_Player)]
                    away_att.append(player_info['attack_vector'].values[0])
                    away_sk.append(player_info['skill_vector'].values[0])
                    away_move.append(player_info['movement_vector'].values[0])
                    away_pow.append(player_info['power_vector'].values[0])
                    away_mental.append(player_info['mentaly_vector'].values[0])
                    away_df.append(player_info['defending_vector'].values[0])
                    away_gk.append(player_info['gk_vector'].values[0])

    sumOne = sum(heapq.nlargest(4, home_att))
    testing_set['svn_home_att_vec'][index] = sumOne
    testing_set['svn_home_sk_vec'][index] = sum(heapq.nlargest(5, home_sk))
    testing_set['svn_home_move_vec'][index] = sum(heapq.nlargest(5, home_move))
    testing_set['svn_home_pow_vec'][index] = sum(heapq.nlargest(5, home_pow))
    testing_set['svn_home_mental_vec'][index] = sum(heapq.nlargest(5, home_mental))
    testing_set['svn_home_df_vec'][index] = sum(heapq.nlargest(4, home_df))
    testing_set['svn_home_gk_vec'][index] = sum(heapq.nlargest(1, home_gk))
    testing_set['svn_away_att_vec'][index] = sum(heapq.nlargest(4, away_att))
    testing_set['svn_away_sk_vec'][index] = sum(heapq.nlargest(5, away_sk))
    testing_set['svn_away_move_vec'][index] = sum(heapq.nlargest(5, away_move))
    testing_set['svn_away_pow_vec'][index] = sum(heapq.nlargest(5, away_pow))
    testing_set['svn_away_mental_vec'][index] = sum(heapq.nlargest(5, away_mental))
    testing_set['svn_away_df_vec'][index] = sum(heapq.nlargest(4, away_df))
    testing_set['svn_away_gk_vec'][index] = sum(heapq.nlargest(1, away_gk))

    home_att = list()
    home_sk = list()
    home_df = list()
    home_gk = list()
    home_move = list()
    home_pow = list()
    home_mental = list()
    away_att = list()
    away_sk = list()
    away_df = list()
    away_gk = list()
    away_move = list()
    away_pow = list()
    away_mental = list()

testing_set.to_csv('D:/testing_test_win.csv')

training_set.to_csv('D:/training_test_win.csv')


# training_set = pd.read_csv("D:/training_test_win.csv")
# testing_set = pd.read_csv("D:/testing_test_win.csv")


x_train = training_set[
    training_set.columns[training_set.columns.isin(['svn_home_att_vec', 'svn_home_sk_vec', 'svn_home_move_vec', 'svn_home_pow_vec',
                                                    'svn_home_mental_vec', 'svn_home_df_vec', 'svn_home_gk_vec', 'svn_away_att_vec',
                                                    'svn_away_sk_vec', 'svn_away_move_vec', 'svn_away_pow_vec', 'svn_away_mental_vec',
                                                    'svn_away_df_vec', 'svn_away_gk_vec'])]]

y_train = training_set[
    training_set.columns[training_set.columns.isin(['predict'])]]


x_test = testing_set[
    testing_set.columns[testing_set.columns.isin(['svn_home_att_vec', 'svn_home_sk_vec', 'svn_home_move_vec', 'svn_home_pow_vec',
                                                    'svn_home_mental_vec', 'svn_home_df_vec', 'svn_home_gk_vec', 'svn_away_att_vec',
                                                    'svn_away_sk_vec', 'svn_away_move_vec', 'svn_away_pow_vec', 'svn_away_mental_vec',
                                                    'svn_away_df_vec', 'svn_away_gk_vec'])]]

y_test = testing_set[
    testing_set.columns[testing_set.columns.isin(['predict'])]]


# fit
svclassifier = SVC(kernel='linear')
svclassifier.fit(x_train, y_train)

# predict
y_pred = svclassifier.predict(x_test)

# evaluation

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))


'''
relevant_df_match.to_csv('data mimi project/test_match.csv')

relevant_df_player_att.to_csv('data mimi project/test.csv')
'''