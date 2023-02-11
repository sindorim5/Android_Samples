# RecyclerView with EditText

## 동작

- 첫 화면에서 노트를 쓰고 Room에 저장
- noteId = 1L을 두번째 화면에 불러온다

## 설명

- 버튼을 누를 때마다 EditText가 추가되는 RecyclerView
- RecyclerView 스크롤 시, EditText 추가 시 값이 마구잡이로 이동하고 바뀌는 문제를 해결하기 위해서
- Adapter의 inner class인 RecyclerView.ViewHolder에서 각각의 아이템에 TextChangedListener를 추가함
- onTextChanged에서 들어오는 CharSequence를 string으로 변환해서 adapterPosition에 알맞게 저장
