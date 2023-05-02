FROM clojure:temurin-17-tools-deps-focal AS base
WORKDIR /app

RUN apt-get update -y
RUN apt-get install ffmpeg -y

FROM base AS deps
COPY deps.edn .
COPY workspace.edn .

RUN clj -X:deps prep :aliases '[:nrepl :dev :poly :test]'

FROM deps AS dev