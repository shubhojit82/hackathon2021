CREATE TABLE hackathon.instacart_txn (
banner text,
txn_id bigint,
upc_id bigint,
email_id text,
first_name text,
household_id bigint,
instacart_order_no bigint,
item_description text,
last_name text,
loyal_cust_wud_hv_paid double,
mail_flag int,
net_amount_paid double,
purchased_qty int,
txn_dt date,
PRIMARY KEY (banner, txn_id, upc_id)
)