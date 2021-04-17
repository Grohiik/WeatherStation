FROM node:15.14.0-slim
WORKDIR /app

COPY package.json .
COPY package-lock.json .
COPY .eslintrc.yml .
RUN npm i

COPY src src

CMD ["npm", "run", "build"]