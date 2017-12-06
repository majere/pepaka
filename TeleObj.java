package sample;

import lombok.Getter;
import java.util.List;

class Obj {
    @Getter private boolean ok;
    @Getter private List<TeleObj> result;
}
class TeleObj {
    @Getter private long update_id;
    @Getter private Message message;
    @Getter private Message edited_message;
    @Getter private Message channel_post;
    @Getter private Message edited_channel_post;
    @Getter private InlineQuery inline_query;
    @Getter private ChosenInlineResult chosen_inline_result;
    @Getter private CallbackQuery callback_query;
    @Getter private ShippingQuery shipping_query;
    @Getter private PreCheckoutQuery pre_checkout_query;
}

class Message {
    @Getter private long message_id;
    @Getter private User from;
    @Getter private int date;
    @Getter private Chat chat;
    @Getter private User forward_from;
    @Getter private Chat forward_from_chat;
    @Getter private long forward_from_message_id;
    @Getter private String forward_signature;
    @Getter private int forward_date;
    @Getter private Message reply_to_message;
    @Getter private int edit_date;
    @Getter private String author_signature;
    @Getter private String text;
    @Getter private List<MessageEntity> entities;
    @Getter private List<MessageEntity> caption_entities;
    @Getter private Audio audio;
    @Getter private Document document;
    @Getter private Game game;
    @Getter private List<PhotoSize> photo;
    @Getter private Sticker sticker;
    @Getter private Video video;
    @Getter private Voice voice;
    @Getter private VideoNote video_note;
    @Getter private String caption;
    @Getter private Contact contact;
    @Getter private Location location;
    @Getter private Venue venue;
    @Getter private List<User> new_chat_members;
    @Getter private User left_chat_member;
    @Getter private String new_chat_title;
    @Getter private List<PhotoSize> new_chat_photo;
    @Getter private boolean delete_chat_photo;
    @Getter private boolean group_chat_created;
    @Getter private boolean supergroup_chat_created;
    @Getter private boolean channel_chat_created;
    @Getter private long migrate_to_chat_id;
    @Getter private long migrate_from_chat_id;
    @Getter private Message pinned_message;
    @Getter private Invoice invoice;
    @Getter private SuccessfulPayment successful_payment;
    @Getter private User new_chat_participant;
}

class PreCheckoutQuery {
    @Getter private String id;
    @Getter private User from;
    @Getter private String currency;
    @Getter private int total_amount;
    @Getter private String invoice_payload;
    @Getter private String shipping_option_id;
    @Getter private OrderInfo order_info;
}
class ShippingQuery {
    @Getter private String id;
    @Getter private User from;
    @Getter private String invoice_payload;
    @Getter private ShippingAddress shipping_address;
}
class CallbackQuery {
    @Getter private long id;
    @Getter private User from;
    @Getter private Message message;
    @Getter private String inline_message_id;
    @Getter private String chat_instance;
    @Getter private String data;
    @Getter private String game_short_name;
}
class ChosenInlineResult {
    @Getter private String result_id;
    @Getter private User from;
    @Getter private Location location;
    @Getter private String inline_message_id;
    @Getter private String query;
}
class InlineQuery {
    @Getter private String id;
    @Getter private User from;
    @Getter private Location location;
    @Getter private String query;
    @Getter private String offset;
}
class SuccessfulPayment {
    @Getter private String currency;
    @Getter private int total_amount;
    @Getter private String invoice_payload;
    @Getter private String shipping_option_id;
    @Getter private OrderInfo order_info;
    @Getter private String telegram_payment_change_id;
    @Getter private String provider_payment_charge_id;
}
class OrderInfo{
    @Getter private String name;
    @Getter private String phone_number;
    @Getter private String email;
    @Getter private ShippingAddress shipping_address;
}
class ShippingAddress {
    @Getter private String country_code;
    @Getter private String state;
    @Getter private String city;
    @Getter private String street_line1;
    @Getter private String street_line2;
    @Getter private String post_code;
}
class Invoice {
    @Getter private String title;
    @Getter private String description;
    @Getter private String start_parameter;
    @Getter private String currency;
    @Getter private long total_amount;
}
class LabeledPrice {
    @Getter private String label;
    @Getter private int amount;
}

class Venue {
    @Getter private Location location;
    @Getter private String title;
    @Getter private String address;
    @Getter private String forsquare_id;
}
class Location {
    @Getter private float longitude;
    @Getter private float latitude;
}
class Chat {
    @Getter private long id;
    @Getter private String type;
    @Getter private String title;
    @Getter private String username;
    @Getter private String first_name;
    @Getter private String last_name;
    @Getter private boolean all_members_are_administrators;
    @Getter private ChatPhoto photo;
    @Getter private String description;
    @Getter private String invite_link;
    @Getter private Message pinned_message;
    @Getter private String sticker_set_name;
    @Getter private boolean can_set_sticker_set;
}
class User {
    @Getter private long id;
    @Getter private boolean is_bot;
    @Getter private String first_name;
    @Getter private String last_name;
    @Getter private String username;
    @Getter private String language_code;
}
class ChatPhoto {
    @Getter private String small_file_id;
    @Getter private String big_file_id;
}
class MessageEntity {
    @Getter private String type;
    @Getter private int offset;
    @Getter private int length;
    @Getter private String url;
    @Getter private User user;
}
class Audio {
    @Getter private String file_id;
    @Getter private int duration;
    @Getter private String performer;
    @Getter private String title;
    @Getter private String mime_type;
    @Getter private int file_size;
}
class Document {
    @Getter private String file_id;
    @Getter private PhotoSize thumb;
    @Getter private String file_name;
    @Getter private String mime_type;
    @Getter private int file_size;
}
class PhotoSize {
    @Getter private String file_id;
    @Getter private int width;
    @Getter private int height;
    @Getter private int file_size;
}
class Game {
    @Getter private String title;
    @Getter private String description;
    @Getter private List<PhotoSize> photo;
    @Getter private String text;
    @Getter private List<MessageEntity> text_entities;
    @Getter private Animation animation;
}
class Animation {
    @Getter private String file_id;
    @Getter private PhotoSize thumb;
    @Getter private String file_name;
    @Getter private String mime_type;
    @Getter private int file_size;
}
class MaskPosition {
    @Getter private String Position;
    @Getter private float x_shift;
    @Getter private float y_shift;
    @Getter private float scale;
}
class Sticker {
    @Getter private String file_id;
    @Getter private int width;
    @Getter private int height;
    @Getter private PhotoSize thumb;
    @Getter private String emoji;
    @Getter private String set_name;
    @Getter private MaskPosition mask_position;
    @Getter private int file_size;
}
class Video {
    @Getter private String file_id;
    @Getter private int width;
    @Getter private int height;
    @Getter private int duration;
    @Getter private PhotoSize thumb;
    @Getter private String mime_type;
    @Getter private int file_size;
}
class Voice {
    @Getter private String file_id;
    @Getter private int duration;
    @Getter private String mime_type;
    @Getter private int file_size;
}
class VideoNote {
    @Getter private String file_id;
    @Getter private int length;
    @Getter private int duration;
    @Getter private PhotoSize thumb;
    @Getter private int file_size;
}
class Contact {
    @Getter private String phone_number;
    @Getter private String first_name;
    @Getter private String last_name;
    @Getter private long user_ud;
}



class AnswerResult {
    @Getter private boolean ok;
    @Getter private Message result;
}