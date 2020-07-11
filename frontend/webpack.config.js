const { resolve } = require("path");

module.exports = {
  resolve: {
    extensions: [".js", ".json", ".vue"],
    root: resolve(__dirname),
    alias: {
      "~": resolve(__dirname),
    },
  },
};
