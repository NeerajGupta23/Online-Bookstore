/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.html",
    "./src/**/*.js",
    "./src/**/*.jsx",
    "./src/**/*.ts",
    "./src/**/*.tsx",
    "./src/**/*.vue",
  ],
  theme: {
    extend: {
      colors: {
        "backgroud-color1": "#090030",
      },
    },
  },
  plugins: [],
};
