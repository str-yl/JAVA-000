CREATE TABLE customer(
customer_id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT ���û�ID��,
login_name VARCHAR(20) NOT NULL COMMENT ���û���¼����,
password CHAR(32) NOT NULL COMMENT ����¼���롯,
user_stats TINYINT NOT NULL DEFAULT 1 COMMENT ���û�״̬��,
identity_card_type TINYINT NOT NULL DEFAULT 1 COMMENT ��֤�����ͣ�1 ���֤��2 ����֤��3 ���ա�,
identity_card_no VARCHAR(20) COMMENT ��֤�����롯,
mobile_phone INT UNSIGNED COMMENT ���ֻ��š�,
customer_email VARCHAR(50) COMMENT �����䡯,
gender CHAR(1) COMMENT ���Ա�,
address VARCHAR(200) NOT NULL COMMENT ���ʼĵ�ַ��,
PRIMARY KEY pk_customerid(customer_id)
) ENGINE = innodb COMMENT ���û���Ϣ��

CREATE TABLE product(
product_id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT ����ƷID��,
product_core CHAR(16) NOT NULL COMMENT ����Ʒ���롯,
product_name VARCHAR(20) NOT NULL COMMENT ����Ʒ���ơ�,
price DECIMAL(8,2) NOT NULL COMMENT ����Ʒ���ۼ۸�,
publish_status TINYINT NOT NULL DEFAULT 0 COMMENT �����¼�״̬��0�¼�1�ϼܡ�,
weight FLOAT COMMENT ����Ʒ������,
length FLOAT COMMENT ����Ʒ���ȡ�,
height FLOAT COMMENT ����Ʒ�߶ȡ�,
width FLOAT COMMENT ����Ʒ��ȡ�,
color_type ENUM(���졯,���ơ�,������,���ڡ�),
production_date DATETIME NOT NULL COMMENT ���������ڡ�,
shelf_life INT NOT NULL COMMENT ����Ʒ��Ч�ڡ�,
descript TEXT NOT NULL COMMENT ����Ʒ������,
indate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ����Ʒ¼��ʱ�䡯,
modified_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ������޸�ʱ�䡯,
PRIMARY KEY pk_productid(product_id)
) ENGINE = innodb COMMENT ����Ʒ��Ϣ��;

CREATE TABLE order(
order_id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT ������ID��,
order_sn BIGINT UNSIGNED NOT NULL COMMENT ��������� yyyymmddnnnnnnnn��,
customer_id INT UNSIGNED NOT NULL COMMENT ���µ���ID��,
product_id INT UNSIGNED NOT NULL COMMENT ��������ƷID��,
product_name VARCHAR(50) NOT NULL COMMENT ����Ʒ���ơ�,
product_cnt INT NOT NULL DEFAULT 1 COMMENT ��������Ʒ������,
product_price DECIMAL(8,2) NOT NULL COMMENT ��������Ʒ���ۡ�,
shipping_user VARCHAR(10) NOT NULL COMMENT ���ջ���������,
address VARCHAR(100) NOT NULL COMMENT ���ջ���ַ��,
payment_method TINYINT NOT NULL COMMENT ��֧����ʽ��1�ֽ�2��3������4֧������5΢�š�,
order_money DECIMAL(8,2) NOT NULL COMMENT ��������,
payment_money DECIMAL(8,2) NOT NULL DEFAULT 0.00 COMMENT ��֧����,
shipping_comp_name VARCHAR(10) COMMENT ����ݹ�˾���ơ�,
shipping_sn VARCHAR(50) COMMENT ����ݵ��š�,
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ���µ�ʱ�䡯,
shipping_time DATETIME COMMENT ������ʱ�䡯,
pay_time DATETIME COMMENT ��֧��ʱ�䡯,
receive_time DATETIME COMMENT ���ջ�ʱ�䡯,
order_status TINYINT NOT NULL DEFAULT 0 COMMENT ������״̬��,
modified_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ������޸�ʱ�䡯,
PRIMARY KEY pk_orderid(order_id)
)ENGINE = innodb COMMENT ��������;