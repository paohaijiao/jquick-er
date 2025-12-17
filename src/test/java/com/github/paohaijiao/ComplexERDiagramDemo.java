package com.github.paohaijiao;

import com.github.paohaijiao.config.ThemeConfig;
import com.github.paohaijiao.enums.RelationshipType;
import com.github.paohaijiao.generator.ERDiagramSVGGenerator;
import com.github.paohaijiao.model.Column;
import com.github.paohaijiao.model.ERDiagram;
import com.github.paohaijiao.model.Table;
import com.github.paohaijiao.relation.Relationship;

import java.awt.*;
import java.util.Date;
import java.util.Random;

public class ComplexERDiagramDemo {
    private static final Random random = new Random();

    public static void main(String[] args) {
        try {
            // 创建ER图 - 使用更大的画布
            ERDiagram diagram = new ERDiagram();
            diagram.setTitle("电商系统完整数据库设计");
            diagram.setAuthor("ER Diagram Generator");
            diagram.setSize(2400, 1600);

            // 创建所有表格（总共约30个表）
            System.out.println("开始创建表格...");

            // 1. 用户模块表格
            Table usersTable = createTable("用户表", "系统用户信息", 350, 200);
            addColumn(usersTable, "user_id", "BIGINT", true, false, false);
            addColumn(usersTable, "username", "VARCHAR(50)", false, false, false);
            addColumn(usersTable, "password", "VARCHAR(100)", false, false, false);
            addColumn(usersTable, "email", "VARCHAR(100)", false, false, false);
            addColumn(usersTable, "mobile", "VARCHAR(20)", false, false, true);
            addColumn(usersTable, "real_name", "VARCHAR(50)", false, false, true);
            addColumn(usersTable, "id_card", "VARCHAR(18)", false, false, true);
            addColumn(usersTable, "avatar", "VARCHAR(200)", false, false, true);
            addColumn(usersTable, "gender", "TINYINT", false, false, true);
            addColumn(usersTable, "birthday", "DATE", false, false, true);
            addColumn(usersTable, "register_time", "DATETIME", false, false, false);
            addColumn(usersTable, "last_login_time", "DATETIME", false, false, true);
            addColumn(usersTable, "status", "TINYINT", false, false, false);

            Table userAddressTable = createTable("用户地址表", "用户收货地址", 320, 180);
            addColumn(userAddressTable, "address_id", "BIGINT", true, false, false);
            addColumn(userAddressTable, "user_id", "BIGINT", false, true, false);
            addColumn(userAddressTable, "receiver_name", "VARCHAR(50)", false, false, false);
            addColumn(userAddressTable, "receiver_phone", "VARCHAR(20)", false, false, false);
            addColumn(userAddressTable, "province", "VARCHAR(50)", false, false, false);
            addColumn(userAddressTable, "city", "VARCHAR(50)", false, false, false);
            addColumn(userAddressTable, "district", "VARCHAR(50)", false, false, false);
            addColumn(userAddressTable, "detail_address", "VARCHAR(200)", false, false, false);
            addColumn(userAddressTable, "is_default", "TINYINT", false, false, false);
            addColumn(userAddressTable, "create_time", "DATETIME", false, false, false);

            Table userBalanceTable = createTable("用户余额表", "用户账户余额", 300, 150);
            addColumn(userBalanceTable, "balance_id", "BIGINT", true, false, false);
            addColumn(userBalanceTable, "user_id", "BIGINT", false, true, false);
            addColumn(userBalanceTable, "balance", "DECIMAL(10,2)", false, false, false);
            addColumn(userBalanceTable, "frozen_balance", "DECIMAL(10,2)", false, false, false);
            addColumn(userBalanceTable, "update_time", "DATETIME", false, false, false);

            Table userLevelTable = createTable("用户等级表", "用户会员等级", 280, 130);
            addColumn(userLevelTable, "level_id", "INT", true, false, false);
            addColumn(userLevelTable, "level_name", "VARCHAR(50)", false, false, false);
            addColumn(userLevelTable, "min_points", "INT", false, false, false);
            addColumn(userLevelTable, "discount_rate", "DECIMAL(3,2)", false, false, false);
            addColumn(userLevelTable, "description", "VARCHAR(200)", false, false, true);

            Table userPointsTable = createTable("用户积分表", "用户积分信息", 300, 150);
            addColumn(userPointsTable, "points_id", "BIGINT", true, false, false);
            addColumn(userPointsTable, "user_id", "BIGINT", false, true, false);
            addColumn(userPointsTable, "total_points", "INT", false, false, false);
            addColumn(userPointsTable, "available_points", "INT", false, false, false);
            addColumn(userPointsTable, "update_time", "DATETIME", false, false, false);

            // 2. 商品模块表格
            Table categoryTable = createTable("商品分类表", "商品分类信息", 320, 180);
            addColumn(categoryTable, "category_id", "BIGINT", true, false, false);
            addColumn(categoryTable, "category_name", "VARCHAR(100)", false, false, false);
            addColumn(categoryTable, "parent_id", "BIGINT", false, true, true);
            addColumn(categoryTable, "level", "TINYINT", false, false, false);
            addColumn(categoryTable, "sort_order", "INT", false, false, false);
            addColumn(categoryTable, "status", "TINYINT", false, false, false);
            addColumn(categoryTable, "icon", "VARCHAR(200)", false, false, true);
            addColumn(categoryTable, "description", "TEXT", false, false, true);

            Table brandTable = createTable("品牌表", "商品品牌信息", 300, 150);
            addColumn(brandTable, "brand_id", "BIGINT", true, false, false);
            addColumn(brandTable, "brand_name", "VARCHAR(100)", false, false, false);
            addColumn(brandTable, "logo", "VARCHAR(200)", false, false, true);
            addColumn(brandTable, "description", "TEXT", false, false, true);
            addColumn(brandTable, "sort_order", "INT", false, false, false);
            addColumn(brandTable, "status", "TINYINT", false, false, false);

            Table goodsTable = createTable("商品表", "商品基本信息", 350, 200);
            addColumn(goodsTable, "goods_id", "BIGINT", true, false, false);
            addColumn(goodsTable, "goods_name", "VARCHAR(200)", false, false, false);
            addColumn(goodsTable, "goods_sn", "VARCHAR(50)", false, false, false);
            addColumn(goodsTable, "category_id", "BIGINT", false, true, false);
            addColumn(goodsTable, "brand_id", "BIGINT", false, true, true);
            addColumn(goodsTable, "market_price", "DECIMAL(10,2)", false, false, false);
            addColumn(goodsTable, "shop_price", "DECIMAL(10,2)", false, false, false);
            addColumn(goodsTable, "cost_price", "DECIMAL(10,2)", false, false, true);
            addColumn(goodsTable, "stock_quantity", "INT", false, false, false);
            addColumn(goodsTable, "warn_quantity", "INT", false, false, false);
            addColumn(goodsTable, "goods_weight", "DECIMAL(8,3)", false, false, true);
            addColumn(goodsTable, "goods_brief", "VARCHAR(500)", false, false, true);
            addColumn(goodsTable, "goods_desc", "TEXT", false, false, true);
            addColumn(goodsTable, "is_on_sale", "TINYINT", false, false, false);
            addColumn(goodsTable, "is_hot", "TINYINT", false, false, false);
            addColumn(goodsTable, "is_new", "TINYINT", false, false, false);
            addColumn(goodsTable, "is_recommend", "TINYINT", false, false, false);
            addColumn(goodsTable, "sort_order", "INT", false, false, false);
            addColumn(goodsTable, "create_time", "DATETIME", false, false, false);
            addColumn(goodsTable, "update_time", "DATETIME", false, false, false);

            Table goodsSpecTable = createTable("商品规格表", "商品规格信息", 320, 180);
            addColumn(goodsSpecTable, "spec_id", "BIGINT", true, false, false);
            addColumn(goodsSpecTable, "goods_id", "BIGINT", false, true, false);
            addColumn(goodsSpecTable, "spec_name", "VARCHAR(100)", false, false, false);
            addColumn(goodsSpecTable, "spec_value", "VARCHAR(100)", false, false, false);
            addColumn(goodsSpecTable, "price_adjust", "DECIMAL(10,2)", false, false, true);
            addColumn(goodsSpecTable, "stock_adjust", "INT", false, false, true);

            Table goodsImageTable = createTable("商品图片表", "商品图片信息", 300, 150);
            addColumn(goodsImageTable, "image_id", "BIGINT", true, false, false);
            addColumn(goodsImageTable, "goods_id", "BIGINT", false, true, false);
            addColumn(goodsImageTable, "image_url", "VARCHAR(200)", false, false, false);
            addColumn(goodsImageTable, "image_desc", "VARCHAR(100)", false, false, true);
            addColumn(goodsImageTable, "sort_order", "INT", false, false, false);
            addColumn(goodsImageTable, "is_main", "TINYINT", false, false, false);

            Table goodsAttributeTable = createTable("商品属性表", "商品属性信息", 320, 180);
            addColumn(goodsAttributeTable, "attribute_id", "BIGINT", true, false, false);
            addColumn(goodsAttributeTable, "goods_id", "BIGINT", false, true, false);
            addColumn(goodsAttributeTable, "attribute_name", "VARCHAR(100)", false, false, false);
            addColumn(goodsAttributeTable, "attribute_value", "VARCHAR(200)", false, false, false);
            addColumn(goodsAttributeTable, "sort_order", "INT", false, false, false);

            // 3. 订单模块表格
            Table orderTable = createTable("订单表", "订单主表", 380, 220);
            addColumn(orderTable, "order_id", "BIGINT", true, false, false);
            addColumn(orderTable, "order_sn", "VARCHAR(50)", false, false, false);
            addColumn(orderTable, "user_id", "BIGINT", false, true, false);
            addColumn(orderTable, "order_status", "TINYINT", false, false, false);
            addColumn(orderTable, "shipping_status", "TINYINT", false, false, false);
            addColumn(orderTable, "pay_status", "TINYINT", false, false, false);
            addColumn(orderTable, "consignee", "VARCHAR(50)", false, false, false);
            addColumn(orderTable, "mobile", "VARCHAR(20)", false, false, false);
            addColumn(orderTable, "address", "VARCHAR(200)", false, false, false);
            addColumn(orderTable, "goods_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderTable, "shipping_fee", "DECIMAL(10,2)", false, false, false);
            addColumn(orderTable, "order_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderTable, "discount_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderTable, "pay_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderTable, "pay_time", "DATETIME", false, false, true);
            addColumn(orderTable, "shipping_time", "DATETIME", false, false, true);
            addColumn(orderTable, "confirm_time", "DATETIME", false, false, true);
            addColumn(orderTable, "create_time", "DATETIME", false, false, false);
            addColumn(orderTable, "update_time", "DATETIME", false, false, false);

            Table orderGoodsTable = createTable("订单商品表", "订单商品明细", 350, 200);
            addColumn(orderGoodsTable, "order_goods_id", "BIGINT", true, false, false);
            addColumn(orderGoodsTable, "order_id", "BIGINT", false, true, false);
            addColumn(orderGoodsTable, "goods_id", "BIGINT", false, true, false);
            addColumn(orderGoodsTable, "goods_name", "VARCHAR(200)", false, false, false);
            addColumn(orderGoodsTable, "goods_sn", "VARCHAR(50)", false, false, false);
            addColumn(orderGoodsTable, "goods_number", "INT", false, false, false);
            addColumn(orderGoodsTable, "market_price", "DECIMAL(10,2)", false, false, false);
            addColumn(orderGoodsTable, "goods_price", "DECIMAL(10,2)", false, false, false);
            addColumn(orderGoodsTable, "spec_info", "VARCHAR(200)", false, false, true);
            addColumn(orderGoodsTable, "is_real", "TINYINT", false, false, false);
            addColumn(orderGoodsTable, "is_gift", "TINYINT", false, false, false);

            Table orderActionTable = createTable("订单操作日志表", "订单操作日志", 330, 180);
            addColumn(orderActionTable, "action_id", "BIGINT", true, false, false);
            addColumn(orderActionTable, "order_id", "BIGINT", false, true, false);
            addColumn(orderActionTable, "action_user", "VARCHAR(50)", false, false, false);
            addColumn(orderActionTable, "order_status", "TINYINT", false, false, false);
            addColumn(orderActionTable, "shipping_status", "TINYINT", false, false, false);
            addColumn(orderActionTable, "pay_status", "TINYINT", false, false, false);
            addColumn(orderActionTable, "action_note", "VARCHAR(500)", false, false, true);
            addColumn(orderActionTable, "log_time", "DATETIME", false, false, false);

            // 4. 购物车模块
            Table cartTable = createTable("购物车表", "用户购物车", 320, 180);
            addColumn(cartTable, "cart_id", "BIGINT", true, false, false);
            addColumn(cartTable, "user_id", "BIGINT", false, true, false);
            addColumn(cartTable, "goods_id", "BIGINT", false, true, false);
            addColumn(cartTable, "goods_sn", "VARCHAR(50)", false, false, false);
            addColumn(cartTable, "goods_name", "VARCHAR(200)", false, false, false);
            addColumn(cartTable, "market_price", "DECIMAL(10,2)", false, false, false);
            addColumn(cartTable, "goods_price", "DECIMAL(10,2)", false, false, false);
            addColumn(cartTable, "goods_number", "INT", false, false, false);
            addColumn(cartTable, "spec_info", "VARCHAR(200)", false, false, true);
            addColumn(cartTable, "selected", "TINYINT", false, false, false);
            addColumn(cartTable, "add_time", "DATETIME", false, false, false);
            addColumn(cartTable, "update_time", "DATETIME", false, false, false);

            // 5. 支付模块
            Table paymentTable = createTable("支付表", "支付信息", 350, 200);
            addColumn(paymentTable, "payment_id", "BIGINT", true, false, false);
            addColumn(paymentTable, "payment_sn", "VARCHAR(50)", false, false, false);
            addColumn(paymentTable, "order_id", "BIGINT", false, true, false);
            addColumn(paymentTable, "user_id", "BIGINT", false, true, false);
            addColumn(paymentTable, "payment_method", "TINYINT", false, false, false);
            addColumn(paymentTable, "payment_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(paymentTable, "payment_status", "TINYINT", false, false, false);
            addColumn(paymentTable, "pay_time", "DATETIME", false, false, true);
            addColumn(paymentTable, "payment_note", "VARCHAR(500)", false, false, true);
            addColumn(paymentTable, "create_time", "DATETIME", false, false, false);

            Table refundTable = createTable("退款表", "退款信息", 330, 180);
            addColumn(refundTable, "refund_id", "BIGINT", true, false, false);
            addColumn(refundTable, "refund_sn", "VARCHAR(50)", false, false, false);
            addColumn(refundTable, "order_id", "BIGINT", false, true, false);
            addColumn(refundTable, "user_id", "BIGINT", false, true, false);
            addColumn(refundTable, "refund_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(refundTable, "refund_status", "TINYINT", false, false, false);
            addColumn(refundTable, "refund_reason", "VARCHAR(500)", false, false, true);
            addColumn(refundTable, "refund_time", "DATETIME", false, false, true);
            addColumn(refundTable, "create_time", "DATETIME", false, false, false);

            // 6. 物流模块
            Table shippingTable = createTable("配送方式表", "物流配送方式", 300, 150);
            addColumn(shippingTable, "shipping_id", "BIGINT", true, false, false);
            addColumn(shippingTable, "shipping_name", "VARCHAR(50)", false, false, false);
            addColumn(shippingTable, "shipping_code", "VARCHAR(50)", false, false, false);
            addColumn(shippingTable, "shipping_desc", "VARCHAR(200)", false, false, true);
            addColumn(shippingTable, "base_price", "DECIMAL(10,2)", false, false, false);
            addColumn(shippingTable, "is_enabled", "TINYINT", false, false, false);
            addColumn(shippingTable, "sort_order", "INT", false, false, false);

            Table deliveryTable = createTable("发货单表", "订单发货信息", 350, 200);
            addColumn(deliveryTable, "delivery_id", "BIGINT", true, false, false);
            addColumn(deliveryTable, "order_id", "BIGINT", false, true, false);
            addColumn(deliveryTable, "delivery_sn", "VARCHAR(50)", false, false, false);
            addColumn(deliveryTable, "shipping_id", "BIGINT", false, true, false);
            addColumn(deliveryTable, "shipping_name", "VARCHAR(50)", false, false, false);
            addColumn(deliveryTable, "consignee", "VARCHAR(50)", false, false, false);
            addColumn(deliveryTable, "mobile", "VARCHAR(20)", false, false, false);
            addColumn(deliveryTable, "address", "VARCHAR(200)", false, false, false);
            addColumn(deliveryTable, "shipping_fee", "DECIMAL(10,2)", false, false, false);
            addColumn(deliveryTable, "shipping_time", "DATETIME", false, false, true);
            addColumn(deliveryTable, "status", "TINYINT", false, false, false);
            addColumn(deliveryTable, "create_time", "DATETIME", false, false, false);

            // 7. 营销模块
            Table couponTable = createTable("优惠券表", "优惠券信息", 350, 200);
            addColumn(couponTable, "coupon_id", "BIGINT", true, false, false);
            addColumn(couponTable, "coupon_name", "VARCHAR(100)", false, false, false);
            addColumn(couponTable, "coupon_type", "TINYINT", false, false, false);
            addColumn(couponTable, "coupon_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(couponTable, "min_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(couponTable, "start_time", "DATETIME", false, false, false);
            addColumn(couponTable, "end_time", "DATETIME", false, false, false);
            addColumn(couponTable, "use_scope", "TINYINT", false, false, false);
            addColumn(couponTable, "total_quantity", "INT", false, false, false);
            addColumn(couponTable, "used_quantity", "INT", false, false, false);
            addColumn(couponTable, "status", "TINYINT", false, false, false);
            addColumn(couponTable, "create_time", "DATETIME", false, false, false);

            Table userCouponTable = createTable("用户优惠券表", "用户领取的优惠券", 330, 180);
            addColumn(userCouponTable, "user_coupon_id", "BIGINT", true, false, false);
            addColumn(userCouponTable, "user_id", "BIGINT", false, true, false);
            addColumn(userCouponTable, "coupon_id", "BIGINT", false, true, false);
            addColumn(userCouponTable, "coupon_sn", "VARCHAR(50)", false, false, false);
            addColumn(userCouponTable, "order_id", "BIGINT", false, true, true);
            addColumn(userCouponTable, "use_status", "TINYINT", false, false, false);
            addColumn(userCouponTable, "use_time", "DATETIME", false, false, true);
            addColumn(userCouponTable, "start_time", "DATETIME", false, false, false);
            addColumn(userCouponTable, "end_time", "DATETIME", false, false, false);
            addColumn(userCouponTable, "create_time", "DATETIME", false, false, false);

            Table promotionTable = createTable("促销活动表", "商品促销活动", 350, 200);
            addColumn(promotionTable, "promotion_id", "BIGINT", true, false, false);
            addColumn(promotionTable, "promotion_name", "VARCHAR(100)", false, false, false);
            addColumn(promotionTable, "promotion_type", "TINYINT", false, false, false);
            addColumn(promotionTable, "discount_rate", "DECIMAL(3,2)", false, false, true);
            addColumn(promotionTable, "discount_amount", "DECIMAL(10,2)", false, false, true);
            addColumn(promotionTable, "start_time", "DATETIME", false, false, false);
            addColumn(promotionTable, "end_time", "DATETIME", false, false, false);
            addColumn(promotionTable, "status", "TINYINT", false, false, false);
            addColumn(promotionTable, "create_time", "DATETIME", false, false, false);

            // 8. 评价模块
            Table commentTable = createTable("商品评价表", "用户商品评价", 350, 200);
            addColumn(commentTable, "comment_id", "BIGINT", true, false, false);
            addColumn(commentTable, "order_id", "BIGINT", false, true, false);
            addColumn(commentTable, "user_id", "BIGINT", false, true, false);
            addColumn(commentTable, "goods_id", "BIGINT", false, true, false);
            addColumn(commentTable, "content", "TEXT", false, false, false);
            addColumn(commentTable, "score", "TINYINT", false, false, false);
            addColumn(commentTable, "is_anonymous", "TINYINT", false, false, false);
            addColumn(commentTable, "is_show", "TINYINT", false, false, false);
            addColumn(commentTable, "create_time", "DATETIME", false, false, false);

            Table commentImageTable = createTable("评价图片表", "评价上传的图片", 300, 150);
            addColumn(commentImageTable, "image_id", "BIGINT", true, false, false);
            addColumn(commentImageTable, "comment_id", "BIGINT", false, true, false);
            addColumn(commentImageTable, "image_url", "VARCHAR(200)", false, false, false);
            addColumn(commentImageTable, "sort_order", "INT", false, false, false);

            // 9. 系统管理模块
            Table adminTable = createTable("管理员表", "系统管理员", 320, 180);
            addColumn(adminTable, "admin_id", "BIGINT", true, false, false);
            addColumn(adminTable, "username", "VARCHAR(50)", false, false, false);
            addColumn(adminTable, "password", "VARCHAR(100)", false, false, false);
            addColumn(adminTable, "real_name", "VARCHAR(50)", false, false, true);
            addColumn(adminTable, "mobile", "VARCHAR(20)", false, false, true);
            addColumn(adminTable, "email", "VARCHAR(100)", false, false, true);
            addColumn(adminTable, "avatar", "VARCHAR(200)", false, false, true);
            addColumn(adminTable, "status", "TINYINT", false, false, false);
            addColumn(adminTable, "last_login_time", "DATETIME", false, false, true);
            addColumn(adminTable, "create_time", "DATETIME", false, false, false);

            Table roleTable = createTable("角色表", "管理员角色", 280, 130);
            addColumn(roleTable, "role_id", "BIGINT", true, false, false);
            addColumn(roleTable, "role_name", "VARCHAR(50)", false, false, false);
            addColumn(roleTable, "role_desc", "VARCHAR(200)", false, false, true);
            addColumn(roleTable, "status", "TINYINT", false, false, false);
            addColumn(roleTable, "create_time", "DATETIME", false, false, false);

            Table permissionTable = createTable("权限表", "系统权限", 300, 150);
            addColumn(permissionTable, "permission_id", "BIGINT", true, false, false);
            addColumn(permissionTable, "permission_name", "VARCHAR(100)", false, false, false);
            addColumn(permissionTable, "permission_code", "VARCHAR(100)", false, false, false);
            addColumn(permissionTable, "parent_id", "BIGINT", false, true, true);
            addColumn(permissionTable, "permission_type", "TINYINT", false, false, false);
            addColumn(permissionTable, "sort_order", "INT", false, false, false);

            Table operationLogTable = createTable("操作日志表", "系统操作日志", 350, 200);
            addColumn(operationLogTable, "log_id", "BIGINT", true, false, false);
            addColumn(operationLogTable, "admin_id", "BIGINT", false, true, true);
            addColumn(operationLogTable, "admin_name", "VARCHAR(50)", false, false, false);
            addColumn(operationLogTable, "operation", "VARCHAR(100)", false, false, false);
            addColumn(operationLogTable, "method", "VARCHAR(10)", false, false, false);
            addColumn(operationLogTable, "params", "TEXT", false, false, true);
            addColumn(operationLogTable, "ip", "VARCHAR(50)", false, false, false);
            addColumn(operationLogTable, "user_agent", "VARCHAR(500)", false, false, true);
            addColumn(operationLogTable, "result", "TINYINT", false, false, false);
            addColumn(operationLogTable, "error_msg", "TEXT", false, false, true);
            addColumn(operationLogTable, "operation_time", "DATETIME", false, false, false);

            // 10. 仓储模块
            Table warehouseTable = createTable("仓库表", "商品仓库", 300, 150);
            addColumn(warehouseTable, "warehouse_id", "BIGINT", true, false, false);
            addColumn(warehouseTable, "warehouse_name", "VARCHAR(100)", false, false, false);
            addColumn(warehouseTable, "warehouse_code", "VARCHAR(50)", false, false, false);
            addColumn(warehouseTable, "address", "VARCHAR(200)", false, false, false);
            addColumn(warehouseTable, "contact", "VARCHAR(50)", false, false, true);
            addColumn(warehouseTable, "phone", "VARCHAR(20)", false, false, true);
            addColumn(warehouseTable, "status", "TINYINT", false, false, false);

            Table stockTable = createTable("库存表", "商品库存", 320, 180);
            addColumn(stockTable, "stock_id", "BIGINT", true, false, false);
            addColumn(stockTable, "goods_id", "BIGINT", false, true, false);
            addColumn(stockTable, "warehouse_id", "BIGINT", false, true, false);
            addColumn(stockTable, "stock_quantity", "INT", false, false, false);
            addColumn(stockTable, "available_quantity", "INT", false, false, false);
            addColumn(stockTable, "locked_quantity", "INT", false, false, false);
            addColumn(stockTable, "update_time", "DATETIME", false, false, false);

            Table stockLogTable = createTable("库存日志表", "库存变更日志", 350, 200);
            addColumn(stockLogTable, "log_id", "BIGINT", true, false, false);
            addColumn(stockLogTable, "goods_id", "BIGINT", false, true, false);
            addColumn(stockLogTable, "warehouse_id", "BIGINT", false, true, false);
            addColumn(stockLogTable, "change_type", "TINYINT", false, false, false);
            addColumn(stockLogTable, "change_quantity", "INT", false, false, false);
            addColumn(stockLogTable, "before_quantity", "INT", false, false, false);
            addColumn(stockLogTable, "after_quantity", "INT", false, false, false);
            addColumn(stockLogTable, "order_id", "BIGINT", false, true, true);
            addColumn(stockLogTable, "operator", "VARCHAR(50)", false, false, false);
            addColumn(stockLogTable, "remark", "VARCHAR(500)", false, false, true);
            addColumn(stockLogTable, "create_time", "DATETIME", false, false, false);

            // 11. 内容管理模块
            Table articleTable = createTable("文章表", "系统文章", 320, 180);
            addColumn(articleTable, "article_id", "BIGINT", true, false, false);
            addColumn(articleTable, "title", "VARCHAR(200)", false, false, false);
            addColumn(articleTable, "category_id", "BIGINT", false, true, false);
            addColumn(articleTable, "content", "TEXT", false, false, false);
            addColumn(articleTable, "author", "VARCHAR(50)", false, false, true);
            addColumn(articleTable, "source", "VARCHAR(100)", false, false, true);
            addColumn(articleTable, "keywords", "VARCHAR(200)", false, false, true);
            addColumn(articleTable, "description", "VARCHAR(500)", false, false, true);
            addColumn(articleTable, "click_count", "INT", false, false, false);
            addColumn(articleTable, "is_show", "TINYINT", false, false, false);
            addColumn(articleTable, "is_top", "TINYINT", false, false, false);
            addColumn(articleTable, "sort_order", "INT", false, false, false);
            addColumn(articleTable, "create_time", "DATETIME", false, false, false);
            addColumn(articleTable, "update_time", "DATETIME", false, false, false);

            // 将所有表格添加到ER图
            System.out.println("添加表格到ER图...");

            Table[] allTables = {
                    usersTable, userAddressTable, userBalanceTable, userLevelTable, userPointsTable,
                    categoryTable, brandTable, goodsTable, goodsSpecTable, goodsImageTable, goodsAttributeTable,
                    orderTable, orderGoodsTable, orderActionTable,
                    cartTable,
                    paymentTable, refundTable,
                    shippingTable, deliveryTable,
                    couponTable, userCouponTable, promotionTable,
                    commentTable, commentImageTable,
                    adminTable, roleTable, permissionTable, operationLogTable,
                    warehouseTable, stockTable, stockLogTable,
                    articleTable
            };

            for (Table table : allTables) {
                diagram.addTable(table);
            }

            // 创建关系
            System.out.println("创建表关系...");

            // 用户模块关系
            createRelationship(diagram, "用户地址表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "用户余额表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "用户积分表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_ONE, "1:1");

            // 商品模块关系
            createRelationship(diagram, "商品表", "category_id", "商品分类表", "category_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品表", "brand_id", "品牌表", "brand_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品规格表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品图片表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品属性表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 订单模块关系
            createRelationship(diagram, "订单表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "订单商品表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "订单商品表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "订单操作日志表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 购物车关系
            createRelationship(diagram, "购物车表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "购物车表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 支付模块关系
            createRelationship(diagram, "支付表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "支付表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "退款表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "退款表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 物流模块关系
            createRelationship(diagram, "发货单表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "发货单表", "shipping_id", "配送方式表", "shipping_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 营销模块关系
            createRelationship(diagram, "用户优惠券表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "用户优惠券表", "coupon_id", "优惠券表", "coupon_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "用户优惠券表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 评价模块关系
            createRelationship(diagram, "商品评价表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "商品评价表", "user_id", "用户表", "user_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品评价表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "评价图片表", "comment_id", "商品评价表", "comment_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 仓储模块关系
            createRelationship(diagram, "库存表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存表", "warehouse_id", "仓库表", "warehouse_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存日志表", "goods_id", "商品表", "goods_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存日志表", "warehouse_id", "仓库表", "warehouse_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存日志表", "order_id", "订单表", "order_id", RelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "角色表", "role_id", "权限表", "permission_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 内容模块关系
            createRelationship(diagram, "文章表", "category_id", "商品分类表", "category_id", RelationshipType.ONE_TO_MANY, "1:N");

            // 创建SVG生成器
            System.out.println("创建SVG生成器...");
            ERDiagramSVGGenerator generator = new ERDiagramSVGGenerator();

            // 配置生成器
            generator.setDiagramName("电商系统数据库ER图");
            generator.setWatermark("Generated by ER Diagram Generator - 电商系统");
            generator.setGenerationDate(new Date());
            generator.setTableSpacing(250); // 增加表格间距

            // 自定义主题
            ThemeConfig theme = new ThemeConfig();
            theme.background = new Color(248, 249, 250);
            theme.tableBackground = Color.WHITE;
            theme.tableHeaderBackground = new Color(240, 241, 242);
            theme.tableBorder = new Color(222, 226, 230);

            // 使用更小的字体以容纳更多内容
            theme.titleFont = new Font("Microsoft YaHei", Font.BOLD, 20);
            theme.tableNameFont = new Font("Microsoft YaHei", Font.BOLD, 12);
            theme.columnFont = new Font("Microsoft YaHei", Font.PLAIN, 10);
            theme.commentFont = new Font("Microsoft YaHei", Font.ITALIC, 9);
            theme.relationshipFont = new Font("Microsoft YaHei", Font.PLAIN, 9);

            generator.setThemeConfig(theme);

            // 生成SVG
            System.out.println("生成SVG文件...");
            generator.generateSVG(diagram, "d://test//complex_er_diagram.svg");

            System.out.println("复杂ER图生成成功: complex_er_diagram.svg");
            System.out.println("总表格数量: " + allTables.length + " 个");
            System.out.println("总关系数量: " + diagram.getRelationships().size() + " 个");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表格的辅助方法
     */
    private static Table createTable(String name, String comment, int width, int height) {
        Table table = new Table(name);
        table.setComment(comment);
        table.setSize(width, height);
        return table;
    }

    /**
     * 添加列的辅助方法
     */
    private static void addColumn(Table table, String name, String dataType,
                                  boolean isPrimaryKey, boolean isForeignKey, boolean nullable) {
        Column column = new Column(name, dataType);
        column.setPrimaryKey(isPrimaryKey);
        column.setForeignKey(isForeignKey);
        column.setNullable(nullable);
        table.addColumn(column);
    }

    /**
     * 创建关系的辅助方法
     */
    private static void createRelationship(ERDiagram diagram, String sourceTable, String sourceColumn,
                                           String targetTable, String targetColumn,
                                           RelationshipType type, String label) {
        Relationship rel = new Relationship(sourceTable, sourceColumn, targetTable, targetColumn, type);
        rel.setLabel(label);
        diagram.addRelationship(rel);
    }
}