FROM node:20 as build-stage

WORKDIR /app

# package*
# 1. package.json => defines the dependencies
# 2. package-lock.json => defines the versions used
COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build --prod

FROM nginx:alpine

# configuration file for the nginx server
# by default nginx searchs for the configuration file in this location
# we need to create the nginx.conf file
COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build-stage /app/dist/book-network-ui /usr/share/nginx/html

EXPOSE 80