import * as React from 'react';
import GameApiClient from "../services/GameApiClient";
import ChallengeApiClient from "../services/ChallengeApiClient";

class LeaderBoardComponent extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            leaderBoard: [],
            serverError: false
        }
    }

    componentDidMount() {
        this.refreshLeaderBoard();
        setInterval(this.refreshLeaderBoard.bind(this), 5000);
    }

    getLeaderBoardData(): Promise {
        return GameApiClient.leaderBoard().then(
            res => {
                if (res.ok) {
                    return res.json();
                } else {
                    return Promise.reject('Gamification: Response Error');
                }
            }
        );
    }

    getUserAliasData(userIds: number[]): Promise {
        return ChallengeApiClient.getUsers(userIds).then(
            res => {
                if (res.ok) {
                    return res.json();
                } else {
                    return Promise.reject('Multiplication: Response Error');
                }
            }
        );
    }

    updateLeaderBoard(leaderBoard) {
        this.setState({
            leaderBoard: leaderBoard,
            serverError: false
        });
    }

    refreshLeaderBoard() {
        this.getLeaderBoardData().then(
            leaderBoardData => {
                let userIds = leaderBoardData.map(row => row.userId);
                if (userIds.length > 0) {
                    this.getUserAliasData(userIds).then(userData => {
                        let userMap = new Map();
                        userData.forEach(idAlias => {
                            userMap.set(idAlias.id, idAlias.alias);
                        });

                        leaderBoardData.forEach(row => row['alias'] = userMap.get(row.userId));
                        this.updateLeaderBoard(leaderBoardData);
                    }).catch(error => {
                        console.log('User Ids Mapping Error', error);
                        this.updateLeaderBoard(leaderBoardData);
                    });
                }
            }
        ).catch(error => {
            this.setState( {serverError: true });
            console.log('Gamification Server Error', error);
        });
    }

    render() {
        if (this.state.serverError) {
            return (<div> We're sorry, but we can't display statistics at this momemnt. </div>)
        }

        return (
            <div>
                <h3> Leaderboard </h3>
                <table>
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Score</th>
                            <th>Badges</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.leaderBoard.map(row =>
                        <tr key={row.userId}>
                            <td>{row.alias ? row.alias : row.uesrId}</td>
                            <td>{row.totalScore}</td>
                            <td>{row.badges.map(badge => <span className="badge" key={badge}> {badge} </span>)}
                            </td>
                        </tr>)}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default LeaderBoardComponent;