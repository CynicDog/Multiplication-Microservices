class ChallengeApiClient {

    static SERVER_URL = 'http://localhost:8080';
    static GET_CHALLENGE = '/challenges/random';
    static POST_RESULT = '/attempts';
    static GET_ATTEMPTS_BY_ALIAS = '/attempts?alias=';
    static GET_USERS = '/users';

    static challenge(): Promise<Response> {
        return fetch(
            ChallengeApiClient.SERVER_URL + ChallengeApiClient.GET_CHALLENGE);
    }

    static sendGuess(user: string, a: number, b: number, guess: number): Promise<Response> {
        return fetch(
            ChallengeApiClient.SERVER_URL + ChallengeApiClient.POST_RESULT,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(
                    {
                        userAlias: user,
                        factorA: a,
                        factorB: b,
                        guess: guess
                    }
                )
            });
    }

    static getAttempts(userAlias: string): Promise<Response> {
        console.log('Get attempts for ' + userAlias);
        return fetch(
            ChallengeApiClient.SERVER_URL + ChallengeApiClient.GET_ATTEMPTS_BY_ALIAS + userAlias);
    }

    static getUsers(userIds: number[]): Promise<Response> {
        return fetch(
            ChallengeApiClient.SERVER_URL + ChallengeApiClient.GET_USERS + '/' + userIds.join(','));
    }
}

export default ChallengeApiClient;