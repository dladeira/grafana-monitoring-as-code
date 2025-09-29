# Grafana Monitoring as Code

Generates basic Grafana dashboard JSON for CPU monitoring using Java + Maven. Project overview:

- `Main.java` - Dashboard generator
- `Dockerfile` - Single-stage Maven build
- `Makefile` - Build commands
- `.github/workflows/ci.yml` - CI pipeline

## The Environment

For development I used a **Makefile**, with three scripts, `json`, `build`, and `clean`.

Under the hood it uses **Docker** (see `Dockerfile`) from the `maven:3.9.8-eclipse-temurin-21` image.

## Basic CI Pipeline

I created a basic CI pipeline using GitHub Actions. It:

1. **Builds** Docker image with Maven + Java 21
2. **Runs** container to generate `latest-dashboard.json`
3. **Commits** the JSON to the repository
4. **Uploads** JSON as artifact for releases/deployment

Note: Commiting the latest JSON to the repository wouldn't be the best idea in production, but was added here due to specific requirements. The artifact isn't used anywhere right now, but serves as a proof of concept, as it then can be used later for release or automatic deployment to a remote grafana server

## Git Convention

The [conventional commits](https://www.conventionalcommits.org/en/v2.0.0/) commit convention was used. Each commit has a clear scope and purpose (for ex `ci`, `feat`, `docs`).

They were also cleaned up after the fact using `git rebase`, to ensure readability for others.
