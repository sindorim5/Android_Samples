# Intro To Compose

## Composable
- Composable 변화를 UI에 반영하고 싶다면 Composable을 다시 그려야 한다.
- state의 변화를 remember해서 UI에 반영한다.

## Imperative vs Declarative

### Imperative (명령형)의 단점
1. Traverse widget tree(expensive)
2. Increase error
3. Changing internal state of the widget manually
4. Update conflicts happen

### Declarative
1. No more manually updating each widget (setters and getters)
2. Data is passed down from the Main Function
3. When event is triggered (user interaction), it sent up.

## Recomposition
- The process of calling composable function again when inputs change