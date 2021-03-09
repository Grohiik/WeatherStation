#  Data Visualization - Web App

A web app for visualizing data.

## Requirements

- [nodejs](https://nodejs.org/en/)

## Development

Under development you'll need to increase the version in the `package.json` file. The value that should be increased is the middle one. Read through the [Docs/README.md](/Docs/README.md) for more info on versioning the code.

This project uses nodejs and npm for bundling the HTML, CSS and TypeScript. You'll need to install the packages with the command below to be able to run the development server.

`cd` into the WebApp directory to start developing.

Install the packages

```
npm install
```

Run the development server

```shell
npm run dev
```

Build the distribution

```shell
npm run build
```

### Visual Studio Code

These extensions are recommended when you're developing on vscode.

**Extensions**

- [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)
- [Prettier - Code formatter](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode)

### Documentation

The code documentation should follow [JSDoc](https://jsdoc.app).

### WebApp Project Structure

```
WebApp
├─ src
│  ├─ scripts
│  │  └─ app.ts
│  ├─ index.html
│  └─ main.css
├─ .eslintrc.yml
├─ .prettierrc
├─ package-lock.json
├─ package.json
└─ README.md
```
