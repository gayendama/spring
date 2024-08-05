insert into item_to_process (id, objet_id, date)
    select
		public.uuid_generate_v4(),
		id,
		date_operation
	from credit_operation_extourne
	order by date_operation desc
ON CONFLICT (objet_id) DO NOTHING