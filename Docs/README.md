# Docs

Welcome to documentation for the WeatherStation project. Here lies the development guideline and API documentations.

## Development

### Branch Naming

#### Prefixes and Suffixes

All branch naming should be `lowercase` with `-` as separators.

| Instance | Branch     | Description                             |
| -------- | ---------- | --------------------------------------- |
| Stable   | `stable`   | Accepts merges from Working and Hotfix. |
| Working  | `main`     | Accepts merges from Features.           |
| Features | `topic-*`  | Branch off Working.                     |
| Hotfix   | `hotfix-*` | Branch off stable for fixes.            |

**Suggested main topic**

| Topic prefix       | Description      |
| :----------------- | :--------------- |
| `topic-embedded-*` | Embedded project |
| `topic-server-*`   | Server project   |
| `topic-web-*`      | WebApp project   |

#### Main Branches

- `main`
- `stable`

### Versioning

This project follows the [Semantic Versioning 2.0.0](https://semver.org/). Make sure to read through it when you're versioning the code.

## Deployment

The `sample.env` file is the sample environment variables for deploying the service with `docker-compose`. Create `.env` in the root of the this project where `docker-compose.yml` lives and paste in the content from the `sample.env`. Change to your own configuration accordingly.

Auto deploy with Github Actions need these value. Create it in the secret tab in the repository settings.

```
SSH_HOST
SSH_USERNAME
SSH_KEY
SSH_PORT
APP_DIRECTORY
```