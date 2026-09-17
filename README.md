# java_batch

定期購入レコードをもとに注文を自動生成する Spring Boot バッチアプリケーション。

---

## 構成

| サービス | 説明 |
|---|---|
| `spring_app` | Spring Boot アプリ（port 8080） |
| `mysql-container` | MySQL 8.4（port 3306） |

---

## 起動方法

```bash
docker compose up
```

初回起動時に以下が自動実行されます。

- `schema.sql` … `subscriptions` / `orders` テーブルの作成
- `data.sql` … `subscriptions` への初期データ投入（3件）

---

## バッチ実行手順

### 今日の日付で実行

```bash
curl -X POST "http://localhost:8080/batch/subscription-order"
```

### 対象日を指定して実行

```bash
curl -X POST "http://localhost:8080/batch/subscription-order?targetDate=2026-07-06"
```

### Docker コンテナ内から実行

```bash
docker exec spring_app curl -s -X POST "http://localhost:8080/batch/subscription-order"
```

### 実行結果の確認

```bash
# ログをリアルタイムで確認
docker logs -f spring_app

# orders テーブルにデータが入っているか確認
docker exec mysql-container mysql -uroot -proot demo -e "SELECT * FROM orders;"
```

---

## バッチ処理の仕様

`SubscriptionOrderBatch` は以下の処理を行います。

1. `subscriptions` テーブルから `next_order_date = 対象日` かつ `status = 'ACTIVE'` のレコードを取得
2. 該当レコード分の注文を `orders` テーブルに INSERT（`order_type = 'SUBSCRIPTION'`）
3. 該当レコードの `next_order_date` を1ヶ月後に更新

---

## 停止方法

```bash
docker compose down
```

データを初期化してやり直す場合は volume も削除します。

```bash
docker compose down -v
```
