# Code Style

Here lies the code style guideline we're trying to follow for WeatherLogger and Embedded. The WebApp follows another style.

## Main points

- The **package** name should be all lowercase.

- When formatting your code you'll need to use `clang-format` with the included `.clang-format`.

- The codebase use 4 spaces as indentation.

- camelCase

- Constant value should be in SNAKE_CASE with all CAPS.

- Use clang-format to fix the code format

## Getting clang-format

For **Windows** the clang-format can be found at https://releases.llvm.org/, head over to the latest `download` link, scroll down to **Pre-Built Binaries** and go into the github page release link.

**NOTE (Windows Install)**: When installing make sure you select `Add LLVM to the system PATH for ...`. This makes sure that you can type `clang-format` in your terminal.

For the **macOS** you can use `brew` to install and **Linux** it is best to use your package manager.

**NOTE**: When formatting make sure you don't put extra newline before opening brace. `clang-format` will always keep at least one newline. Example down below is only the illustration on what not to do when formatting with `clang-format`. This is not how the style should look like.

```cpp
void hello()

{} // BAD
```

```cpp
void hello()
{} // OK
```

### Terminal

You can format your code in the terminal with this command

```
clang-format -i <filepath>
```

### clang-format with IntelliJ

To use `clang-format` with IntelliJ you'll need to create an external tool.

To create a tool you can

1. Go to `File → Settings... → Tools → External Tools`.
2. Add a new tool by pressing the plus icon.
3. Give it a name such as `clang-format`.
4. In `Program:` field you can take the whole path to where clang-format executable is on your system. If your environment variable is already set you can just input `clang-format`.
5. In `Arguments:` you need to input these values `-i "$FilePath$"`.
6. In `Working directory:` you need `$FileDir$`

Now when you want to format your code you can go to `Tools → External Tools → clang-format` and it'll run the `clang-format` command for you and reformat the file you're currently looking at.
