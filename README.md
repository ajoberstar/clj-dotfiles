# clj-dotfiles

[![Travis](https://img.shields.io/travis/ike-tools/ike.cljj.svg?style=flat-square)](https://travis-ci.org/ajoberstar/clj-dotfiles)
[![GitHub license](https://img.shields.io/github/license/ike-tools/ike.cljj.svg?style=flat-square)](https://github.com/ajoberstar/clj-dotfiles/blob/master/LICENSE)
[![Clojars](https://img.shields.io/clojars/v/ike/ike.cljj.svg?style=flat-square)](http://clojars.org/org.ajoberstar/clj-dotfiles)

## What is it?

A dotfiles helper to symlink from your home directory to your versioned dotfiles
directory.

### Current Support

* Symlinking files or directories from dotfiles directory to home directory.

## Usage

**NOTE:** clj-dotfiles requires Java 7 or higher to be installed.

* [Release Notes](https://github.com/ajoberstar/clj-dotfiles/releases)

### Installation and Configuration

* Create a dotfiles directory (e.g. `/home/myname/.dotfiles` or `C:\Users\MyName\dotfiles`).
* Download one or both of the platform-specific [scripts](https://github.com/ajoberstar/clj-dotfiles/tree/master/scripts).
* Populate the dotfiles directory with the files you are
* Create a `config-linux.edn` and/or `config-win.edn` file to store which files/directories get copied on each platform.

#### Example Directory Structure

```
+ dotfiles
  + gradle-init.d
    - ....gradle init scripts...
  + vim
    - ...vim config...
  - bashrc
  - config-linux.edn
  - config-win.edn
  - install
  - install.ps1
  - vimrc
```

#### Example Config EDN

The key will be the path relative to your dotfiles directory. The value is the path relative to your home directory the
symlink should be placed in.

```edn
{"vimrc" ".vimrc"
 "vim" ".vim"
 "bashrc" ".bashrc"
 "gradle-init.d" ".gradle/init.d"}
```

#### Resulting Home Directory

```
+ home
  + .vim => ~/dotfiles/vim
  + .gradle
    + init.d => ~/dotfiles/gradle-init.d
  + dotfiles
  - .bashrc => ~/dotfiles/bashrc
  - .vimrc => ~/dotfiles/vimrc
```

### Execution

**NOTE:** Windows requires administrator priveleges to create symbolic links.

Just run `./install` in your dotfiles directory. If any state is invalid, the script will fail.

## Questions, Bugs, and Features

Please use the repo's [issues](https://github.com/ajoberstar/clj-dotfiles/issues)
for all questions, bug reports, and feature requests.

## Contributing

Contributions are very welcome and are accepted through pull requests.

Smaller changes can come directly as a PR, but larger or more complex
ones should be discussed in an issue first to flesh out the approach.

If you're interested in implementing a feature on the
[issues backlog](https://github.com/ajoberstar/clj-dotfiles/issues), add a comment
to make sure it's not already in progress and for any needed discussion.

## License

Copyright © 2015 Andrew Oberstar

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
